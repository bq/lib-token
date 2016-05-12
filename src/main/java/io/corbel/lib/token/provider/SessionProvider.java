package com.bq.corbel.lib.token.provider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.CookieParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.ext.Provider;

import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;
import org.glassfish.jersey.server.internal.inject.AbstractValueFactoryProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.internal.inject.ParamInjectionResolver;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bq.corbel.lib.token.exception.TokenVerificationException;
import com.bq.corbel.lib.token.parser.TokenParser;
import com.bq.corbel.lib.token.reader.TokenReader;
import com.google.common.base.Optional;

public class SessionProvider {

    private static final Logger LOG = LoggerFactory.getLogger(SessionProvider.class);

    private static TokenParser tokenParser;

    public SessionProvider(TokenParser tokenParser) {
        this.tokenParser = tokenParser;
    }

    public org.glassfish.hk2.utilities.Binder getBinder() {
        return new Binder();
    }

    public static class Binder extends AbstractBinder {
        @Override
        protected void configure() {
            bind(SessionFactoryProvider.class).to(ValueFactoryProvider.class).in(Singleton.class);
            bind(SessionInjectionResolver.class).to(new TypeLiteral<InjectionResolver<CookieParam>>() {}).in(Singleton.class);
        }
    }

    public static final class SessionInjectionResolver extends ParamInjectionResolver<CookieParam> {
        public SessionInjectionResolver() {
            super(SessionFactoryProvider.class);
        }
    }

    @Provider public static class SessionFactoryProvider extends AbstractValueFactoryProvider {

        @Inject
        protected SessionFactoryProvider(MultivaluedParameterExtractorProvider mpep, ServiceLocator locator) {
            super(mpep, locator, Parameter.Source.COOKIE);
        }

        @Override
        protected AbstractContainerRequestValueFactory<?> createValueFactory(Parameter parameter) {
            String parameterName = parameter.getSourceName();
            if (parameterName == null || parameterName.length() == 0) {
                // Invalid cookie parameter name
                return null;
            }

            if (isOptionalSession(parameter.getType())) {
                return new OptionalSessionInjectable(parameterName);
            }
            if (TokenReader.class.isAssignableFrom(parameter.getRawType())) {
                return new SessionInjectable(parameterName);
            }
            return null;
        }



        private class OptionalSessionInjectable extends AbstractContainerRequestValueFactory<Optional<TokenReader>> {

            private final SessionInjectable sessionInjectable;

            public OptionalSessionInjectable(String cookieKey) {
                sessionInjectable = new SessionInjectable(cookieKey);
            }

            @Override
            public Optional<TokenReader> provide() {
                return Optional.fromNullable(sessionInjectable.provide());
            }
        }

        private class SessionInjectable extends AbstractContainerRequestValueFactory<TokenReader> {

            private final String cookieKey;

            public SessionInjectable(String cookieKey) {
                this.cookieKey = cookieKey;
            }

            @Override
            public TokenReader provide() {
                Cookie cookie = getContainerRequest().getCookies().get(cookieKey);
                if (cookie != null) {
                    try {
                        return tokenParser.parseAndVerify(cookie.getValue());
                    } catch (TokenVerificationException e) {
                        LOG.warn("Received invalid session cookie {}", cookie);
                    }
                }
                return null;
            }
        }

        private boolean isOptionalSession(Type parameterType) {
            if (parameterType instanceof ParameterizedType) {
                ParameterizedType generic = (ParameterizedType) parameterType;
                if (generic.getRawType().equals(Optional.class)) {
                    return generic.getActualTypeArguments()[0].equals(TokenReader.class);
                }
            }
            return false;
        }
    }

}

package com.jd.demo.conf;

import com.thclouds.commons.base.exceptions.BusException;
import com.thclouds.commons.base.exceptions.CheckedException;
import com.thclouds.commons.base.exceptions.NetConnectionException;
import com.thclouds.commons.base.exceptions.SysErrorException;
import com.thclouds.commons.sop.response.SopResponse;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties({ServerProperties.class, ResourceProperties.class})
public class ExceptionHandlerConfiguration {

    private final ServerProperties serverProperties;
    private final ApplicationContext applicationContext;
    private final ResourceProperties resourceProperties;
    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    private static Map<Class<? extends Throwable>, HttpStatus> exceptionhttpStatusMapping = new HashMap<>();

    static {

        exceptionhttpStatusMapping.put(BusException.class, HttpStatus.OK);
        exceptionhttpStatusMapping.put(NetConnectionException.class, HttpStatus.OK);
        exceptionhttpStatusMapping.put(SysErrorException.class, HttpStatus.OK);
        exceptionhttpStatusMapping.put(CheckedException.class, HttpStatus.OK);
        //500
        exceptionhttpStatusMapping.put(Exception.class, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ExceptionHandlerConfiguration(ServerProperties serverProperties, ResourceProperties resourceProperties, ObjectProvider<ViewResolver> viewResolversProvider, ServerCodecConfigurer serverCodecConfigurer, ApplicationContext applicationContext) {
        this.serverProperties = serverProperties;
        this.applicationContext = applicationContext;
        this.resourceProperties = resourceProperties;
        this.viewResolvers = (List) viewResolversProvider.orderedStream().collect(Collectors.toList());
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Bean
    @Order(-1)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ErrorAttributes errorAttributes) {
        WebExceptionHandler exceptionHandler = new WebExceptionHandler(
                errorAttributes, resourceProperties, this.serverProperties.getError(), applicationContext);
        exceptionHandler.setViewResolvers(this.viewResolvers);
        exceptionHandler.setMessageWriters(this.serverCodecConfigurer.getWriters());
        exceptionHandler.setMessageReaders(this.serverCodecConfigurer.getReaders());
        return exceptionHandler;
    }


    public class WebExceptionHandler extends DefaultErrorWebExceptionHandler {
        public WebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
            super(errorAttributes, resourceProperties, errorProperties, applicationContext);
        }

        /**
         * 渲染异常Response
         *
         * @param request
         * @return
         */
        @Override
        protected Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
            Throwable error = super.getError(request);
            error.printStackTrace();
            HttpStatus httpStatus = exceptionhttpStatusMapping.get(error.getClass());

            if (Objects.nonNull(httpStatus) ) {
                /*有自定义的用自定义错误响应*/
                SopResponse response = new SopResponse();
                response.setCode(String.valueOf(httpStatus.value()));
                response.setMsgKey("failed");
                response.setMsgDesc(error.getMessage());
                response.setData(null);
                return ServerResponse.status(httpStatus)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(response));
            } else {
                /*未自定义的用框架的默认错误响应*/
                return super.renderErrorResponse(request);
            }
        }

    }
}

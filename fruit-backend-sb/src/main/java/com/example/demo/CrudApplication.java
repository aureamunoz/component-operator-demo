/*
 * Copyright 2016-2017 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.demo;

import io.ap4k.component.annotation.CompositeApplication;
import io.ap4k.component.annotation.Link;
import io.ap4k.component.model.Kind;
import io.ap4k.kubernetes.annotation.Annotation;
import io.ap4k.kubernetes.annotation.Env;
import io.ap4k.kubernetes.annotation.KubernetesApplication;
import io.ap4k.servicecatalog.annotation.Parameter;
import io.ap4k.servicecatalog.annotation.ServiceCatalog;
import io.ap4k.servicecatalog.annotation.ServiceCatalogInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@KubernetesApplication(
    annotations = {
        @Annotation(key = "app.openshift.io/artifact-copy-args", value = "*.jar"),
        @Annotation(key = "app.openshift.io/component-name", value = "fruit-backend-sb"),
        @Annotation(key = "app.openshift.io/git-dir", value = "fruit-backend-sb"),
        @Annotation(key = "app.openshift.io/git-ref", value = "master"),
        @Annotation(key = "app.openshift.io/git-uri", value = "https://github.com/snowdrop/component-operator-demo.git"),
        @Annotation(key = "app.openshift.io/java-app-jar", value = "fruit-backend-sb-0.0.1-SNAPSHOT.jar"),
        @Annotation(key = "app.openshift.io/runtime-image", value = "fruit-backend-sb")
    })
@CompositeApplication(
    name = "fruit-backend-sb",
    exposeService = true,
    envVars = @Env(
        name = "SPRING_PROFILES_ACTIVE",
        value = "openshift-catalog")
)
@ServiceCatalog(
    instances = @ServiceCatalogInstance(
        name = "postgresql-db",
        serviceClass = "dh-postgresql-apb",
        servicePlan = "dev",
        bindingSecret = "postgresql-db",
        parameters = {
            @Parameter(key = "postgresql_user", value = "luke"),
            @Parameter(key = "postgresql_password", value = "secret"),
            @Parameter(key = "postgresql_database", value = "my_data"),
            @Parameter(key = "postgresql_version", value = "9.6")
        }
    )
)
@Link(
    name = "Secret to be injected as EnvVar using Service's secret",
    targetcomponentname = "fruit-backend-sb",
    kind = Kind.Secret,
    ref = "postgresql-db")
@SpringBootApplication
public class CrudApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }
    
}

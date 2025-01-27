/*
 * Copyright 2016-2017 Red Hat, Inc, and individual contributors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point to the application.
 */
@SpringBootApplication
@KubernetesApplication(
    annotations = {
        @Annotation(key = "app.openshift.io/artifact-copy-args", value = "*.jar"),
        @Annotation(key = "app.openshift.io/component-name", value = "fruit-client-sb"),
        @Annotation(key = "app.openshift.io/git-dir", value = "fruit-client-sb"),
        @Annotation(key = "app.openshift.io/git-ref", value = "master"),
        @Annotation(key = "app.openshift.io/git-uri", value = "https://github.com/snowdrop/component-operator-demo.git"),
        @Annotation(key = "app.openshift.io/java-app-jar", value = "fruit-client-sb-0.0.1-SNAPSHOT.jar"),
        @Annotation(key = "app.openshift.io/runtime-image", value = "fruit-client-sb")
    })
@CompositeApplication(
    name = "fruit-client-sb",
    exposeService = true
)
@Link(
    name = "Env var to be injected within the target component -> fruit-backend",
    targetcomponentname = "fruit-client-sb",
    kind = Kind.Env,
    envVars = @Env(
        name = "OPENSHIFT_ENDPOINT_BACKEND",
        value = "http://fruit-backend-sb:8080/api/fruits"
    )
)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}


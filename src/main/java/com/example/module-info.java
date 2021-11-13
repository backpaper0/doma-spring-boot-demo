module com.example.app {
    requires doma.spring.boot.autoconfigure;
    requires doma.spring.boot.core;
    requires java.annotation;
    requires java.instrument;
    requires org.seasar.doma.core;
    requires org.seasar.doma.processor;
    requires spring.beans;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires spring.tx;
    requires spring.web;
    opens com.example;
}

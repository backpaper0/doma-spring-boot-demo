module com.example.app {
    requires java.annotation;
    requires org.seasar.doma.core;
    requires org.seasar.doma.processor;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.beans;
    requires spring.context;
    requires spring.tx;
    requires spring.web;
    requires org.seasar.doma.boot.autoconfigure;
    requires org.seasar.doma.boot.core;
    requires java.instrument;
    opens com.example;
}

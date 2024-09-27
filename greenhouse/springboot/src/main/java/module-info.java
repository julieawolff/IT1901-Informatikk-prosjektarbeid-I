module greenhouse.springboot {
  requires com.fasterxml.jackson.databind;

  requires spring.web;
  requires spring.beans;
  requires spring.boot;
  requires spring.context;
  requires spring.boot.autoconfigure;

  requires greenhouse.core;

  opens greenhouse.springboot.restserver to spring.beans, spring.context, spring.web;
}
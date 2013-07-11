package com.solveit;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import com.google.inject.Injector;
import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.spi.InjectionProviderException;
import com.sun.faces.vendor.WebContainerInjectionProvider;

public class GuiceInjectionProvider implements InjectionProvider {

    private final WebContainerInjectionProvider injectionProvider = new WebContainerInjectionProvider();

    @Override
    public void inject(Object managedBean) throws InjectionProviderException {
        getInjector().injectMembers(managedBean);
        injectionProvider.inject(managedBean);
    }

    @Override
    public void invokePostConstruct(Object managedBean) throws InjectionProviderException {
        injectionProvider.invokePostConstruct(managedBean);
    }

    @Override
    public void invokePreDestroy(Object managedBean) throws InjectionProviderException {
        injectionProvider.invokePreDestroy(managedBean);
    }

    private Injector getInjector() {
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        Object injector = context.getAttribute(Injector.class.getName());
        if (injector != null) {
            return (Injector) injector;
        }

        throw new NullPointerException("guice injector can't be null, check getInjector method on context listener");
    }
}
package com.munin.mhcomplier;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

public class PrintProcessMsgUtils {

    public static void print(ProcessingEnvironment processingEnv, Diagnostic.Kind kind, Element element, String message, Object... args){
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(kind, message, element);
    }
}

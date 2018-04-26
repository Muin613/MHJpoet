package com.munin.mhcomplier;

import com.google.auto.service.AutoService;
import com.munin.mhannotation.MHAnnoClass;
import com.munin.mhannotation.MHMain;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


//https://blog.csdn.net/qq_26376637/article/details/52374063

@AutoService(Processor.class)
public class MHClassProcessor extends AbstractProcessor {

    private Messager messager;
    private Types typeUtils;
    private Filer mFiler;
    private Elements mElementsUtils;

    private static final ClassName main = ClassName.get("com.munin.mhannotation", "MHMain");

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
        mFiler = processingEnv.getFiler();
        mElementsUtils = processingEnv.getElementUtils();
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(MHAnnoClass.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        LinkedHashMap<Element, List<MHClass>> classMap = new LinkedHashMap<>();
        Collection<? extends Element> mhClassSet = roundEnvironment.getElementsAnnotatedWith(MHAnnoClass.class);
        for (Element e : mhClassSet) {
            if (e.getKind() != ElementKind.CLASS) {
                messager.printMessage(Diagnostic.Kind.ERROR, "only support class");
                continue;
            }
            Element typeElement = e;
            List<MHClass> findClass = classMap.get(typeElement);
            if (findClass == null) {
                findClass = new ArrayList<>();
                classMap.put(typeElement, findClass);
            }
            MHClass cls = new MHClass();
            cls.setCls(e);
            findClass.add(cls);
        }
        for (Map.Entry<Element, List<MHClass>> item : classMap.entrySet()) {
            Element closeTypeElement = item.getKey();
            String packageName = mElementsUtils.getPackageOf(closeTypeElement).getQualifiedName().toString();
            String className = item.getKey().getSimpleName().toString();
            String teminalClassName = className + "$$YM";
            TypeSpec.Builder createClass = TypeSpec.classBuilder(teminalClassName)
                    .addModifiers(Modifier.PUBLIC);
            FieldSpec fieldSpec = FieldSpec.builder(MHMain.class, "mhMain", Modifier.PRIVATE).build();
            MethodSpec.Builder constructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
                    .addStatement("this.mhMain=new $T()", main);
            createClass.addField(fieldSpec);
            createClass.addMethod(constructor.build());
            JavaFile javaFile = JavaFile.builder(packageName, createClass.build()).build();
            try {
                javaFile.writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
                PrintProcessMsgUtils.print(processingEnv,Diagnostic.Kind.ERROR, closeTypeElement,"Unable to write binding for type %s: %s", e.getMessage());
            }

        }
        return true;
    }


}

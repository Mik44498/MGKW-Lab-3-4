package pl.edu.wat.courses.reflection;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;

import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class Reflection {
    private TypeDescription entityDefinition;
    private TypeDescription requestDefinition;
    private TypeDescription responseDefinition;
    private TypeDescription mapperDefinition;
    private TypePool typePool;
    private ByteBuddy byteBuddy;

    public Reflection() {
        this.typePool = TypePool.Default.ofSystemLoader();
        this.byteBuddy = new ByteBuddy();
        this.entityDefinition = typePool.describe("pl.edu.wat.courses.entity.User").resolve();
        this.requestDefinition = typePool.describe("pl.edu.wat.courses.dto.UserRequest").resolve();
        this.responseDefinition = typePool.describe("pl.edu.wat.courses.dto.UserResponse").resolve();
        this.mapperDefinition = typePool.describe("pl.edu.wat.courses.mapper.UserMapper").resolve();

    }
    public static void apply(String fieldName, String fieldType) {
        var ref = new Reflection();
        ref.applyEntity(fieldName, fieldType);
        ref.applyRequest(fieldName, fieldType);
        ref.applyResponse(fieldName, fieldType);
        ref.applyUserMapper(fieldName);
    }

    private void applyUserMapper(String fieldName) {
        TypePool typePool = TypePool.Default.ofSystemLoader();
        ByteBuddy byteBuddy = new ByteBuddy();
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(mapperDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .method(named("fillUserRequest"))
                .intercept(MethodCall.invoke(setterUserEntity(fieldName))
                        .onArgument(0)
                        .withMethodCall(MethodCall
                                .invoke(getterRequest(fieldName))
                                .onArgument(1)))
                .method(named("fillUser"))
                .intercept(MethodCall.invoke(setterUserResponse(fieldName))
                        .onArgument(0)
                        .withMethodCall(MethodCall
                                .invoke(getterEntity(fieldName))
                                .onArgument(1)));

        try (var unloadedAuthor = builder.make()) {
            mapperDefinition =  unloadedAuthor.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MethodDescription getterEntity(String fieldName) {
        return entityDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isGetter(fieldName))
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private MethodDescription setterUserResponse(String fieldName) {
        return responseDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isSetter(fieldName))
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private MethodDescription getterRequest(String fieldName) {
        return requestDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isGetter(fieldName))
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private MethodDescription setterUserEntity(String fieldName) {
        return entityDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isSetter(fieldName))
                .stream()
                .findFirst()
                .orElseThrow();
    }


    private void applyResponse(String fieldName, String fieldType) {
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(responseDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineProperty(fieldName, typePool.describe(fieldType).resolve());

        try (var unloadedUser = builder.make()) {
            responseDefinition = unloadedUser.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void applyRequest(String fieldName, String fieldType) {
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(requestDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineProperty(fieldName, typePool.describe(fieldType).resolve());

        try (var unloadedAuthor = builder.make()) {
            requestDefinition = unloadedAuthor.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void applyEntity(String fieldName, String fieldType) {
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(entityDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineProperty(fieldName, typePool.describe(fieldType).resolve());

        try (var unloadedUser = builder.make()) {
            entityDefinition = unloadedUser.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
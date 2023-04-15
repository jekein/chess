package org.example;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private long glfwWindow;

    private final int HEIGHT = 300;

    private final int WIDTH = 300;

    private final String TITLE = "Chess";

    private static Window window = null;

    private Window() {
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();
    }

    private void init() {
        try(GLFWErrorCallback print = GLFWErrorCallback.createPrint(System.err)) {
            print.set();
        }

        if (!glfwInit()) {
            throw new IllegalStateException("ошибка инициализации");
        }

        //Конфигурация GLFW
        glfwDefaultWindowHints(); //захват окна по умолчанию
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE); //окно остается скрытым после создания
        glfwWindowHint(GLFW_RESIZABLE,GLFW_TRUE); //МОЖНО МЕНЯТЬ РАЗМЕР ОКНА
        glfwWindowHint(GLFW_MAXIMIZED,GLFW_TRUE); //окно максимального размера

        glfwWindow = glfwCreateWindow(WIDTH, HEIGHT, TITLE, NULL, NULL);

        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);
    }

    private void loop() {
        GL.createCapabilities();

        float r, g, b, a;
        r = 1.0f;
        g = 1.0f;
        b = 1.0f;
        a = 1.0f;


        while (!glfwWindowShouldClose(glfwWindow)) {
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glfwSwapBuffers(glfwWindow);
            if (MouseListener.isMouseButtonPressed(GLFW_MOUSE_BUTTON_1)){
                r = 0.0f;
                g = 0.0f;
                b = 0.0f;
            }
            if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)){
                r = 0.0f;
                g = 1.0f;
                b = 0.0f;
            }
        }
    }
}

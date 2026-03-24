    package libraryManagment.app;

    import libraryManagment.config.AppConfig;
    import libraryManagment.ui.ConsoleUi;

    public class Main {
        public static void main(String[] args) {
            ConsoleUi app = AppConfig.createApp();
            app.start();
        }
    }

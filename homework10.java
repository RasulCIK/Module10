import java.util.ArrayList;
import java.util.List;

class TV {
    public void turnOn() { System.out.println("TV is turned on."); }
    public void turnOff() { System.out.println("TV is turned off."); }
    public void setChannel(int channel) { System.out.println("TV channel set to " + channel + "."); }
}

class AudioSystem {
    public void turnOn() { System.out.println("Audio system is turned on."); }
    public void turnOff() { System.out.println("Audio system is turned off."); }
    public void setVolume(int level) { System.out.println("Audio volume is set to " + level + "."); }
}

class DVDPlayer {
    public void play() { System.out.println("DVD is playing."); }
    public void pause() { System.out.println("DVD is paused."); }
    public void stop() { System.out.println("DVD is stopped."); }
}

class GameConsole {
    public void turnOn() { System.out.println("Game console is turned on."); }
    public void startGame() { System.out.println("Game has started on the console."); }
}

class HomeTheaterFacade {
    private TV tv;
    private AudioSystem audio;
    private DVDPlayer dvdPlayer;
    private GameConsole gameConsole;

    public HomeTheaterFacade(TV tv, AudioSystem audio, DVDPlayer dvdPlayer, GameConsole gameConsole) {
        this.tv = tv;
        this.audio = audio;
        this.dvdPlayer = dvdPlayer;
        this.gameConsole = gameConsole;
    }

    public void watchMovie() {
        System.out.println("Preparing to watch a movie...");
        tv.turnOn();
        audio.turnOn();
        audio.setVolume(5);
        dvdPlayer.play();
    }

    public void endMovie() {
        System.out.println("Shutting down the movie...");
        dvdPlayer.stop();
        audio.turnOff();
        tv.turnOff();
    }

    public void playGame() {
        System.out.println("Starting game console...");
        tv.turnOn();
        gameConsole.turnOn();
        gameConsole.startGame();
    }

    public void listenToMusic() {
        System.out.println("Setting up for music...");
        tv.turnOn();
        audio.turnOn();
        audio.setVolume(7);
    }

    public void setVolume(int level) {
        audio.setVolume(level);
    }
}


abstract class FileSystemComponent {
    protected String name;

    public FileSystemComponent(String name) {
        this.name = name;
    }

    public abstract void display();
    public abstract int getSize();

    public void add(FileSystemComponent component) { throw new UnsupportedOperationException(); }
    public void remove(FileSystemComponent component) { throw new UnsupportedOperationException(); }
}

class File extends FileSystemComponent {
    private int size;

    public File(String name, int size) {
        super(name);
        this.size = size;
    }

    @Override
    public void display() {
        System.out.println("File: " + name + ", Size: " + size);
    }

    @Override
    public int getSize() {
        return size;
    }
}

class Directory extends FileSystemComponent {
    private List<FileSystemComponent> components = new ArrayList<>();

    public Directory(String name) {
        super(name);
    }

    @Override
    public void add(FileSystemComponent component) {
        if (!components.contains(component)) {
            components.add(component);
        }
    }

    @Override
    public void remove(FileSystemComponent component) {
        components.remove(component);
    }

    @Override
    public void display() {
        System.out.println("Directory: " + name);
        for (FileSystemComponent component : components) {
            component.display();
        }
    }

    @Override
    public int getSize() {
        int totalSize = 0;
        for (FileSystemComponent component : components) {
            totalSize += component.getSize();
        }
        return totalSize;
    }
}


 class Main {
    public static void main(String[] args) {
        TV tv = new TV();
        AudioSystem audio = new AudioSystem();
        DVDPlayer dvdPlayer = new DVDPlayer();
        GameConsole gameConsole = new GameConsole();

        HomeTheaterFacade homeTheater = new HomeTheaterFacade(tv, audio, dvdPlayer, gameConsole);
        homeTheater.watchMovie();
        System.out.println();
        homeTheater.endMovie();
        System.out.println();
        homeTheater.playGame();
        System.out.println();
        homeTheater.listenToMusic();
        System.out.println();
        homeTheater.setVolume(10);

        System.out.println("\n--- File System ---");
        File file1 = new File("Document.txt", 120);
        File file2 = new File("Photo.jpg", 200);
        File file3 = new File("Video.mp4", 1500);

        Directory downloads = new Directory("Downloads");
        downloads.add(file1);
        downloads.add(file2);

        Directory videos = new Directory("Videos");
        videos.add(file3);

        Directory root = new Directory("Root");
        root.add(downloads);
        root.add(videos);

        System.out.println("File system structure:");
        root.display();
        System.out.println("Total size of root directory: " + root.getSize() + " KB");
    }
}

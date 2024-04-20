import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

class Toy {
    String id;
    String name;
    int weight;

    public Toy(String id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }
}

class ToyStore {
    ArrayList<Toy> toys;
    PriorityQueue<Integer> queue;
    Random random;

    public ToyStore() {
        toys = new ArrayList<>();
        queue = new PriorityQueue<>((a, b) -> Integer.compare(toys.get(b-1).weight, toys.get(a-1).weight));
        random = new Random();
    }

    public void put(String toyInfo) {
        String[] infoParts = toyInfo.split(" ");
        String id = infoParts[0];
        int weight = Integer.parseInt(infoParts[1]);
        String name = infoParts[2];
        toys.add(new Toy(id, name, weight));
        queue.add(Integer.parseInt(id));
    }

    public String get() {
        if (!queue.isEmpty()) {
            return toys.get(queue.poll()-1).name;
        }
        return null;
    }

    public void runDraw() {
        try(FileWriter writer = new FileWriter("result.txt")) {
            for (int i = 0; i < 10; i++) {
                String result = get();
                if (result != null) {
                    writer.write(result + "\n");
                }
                // после выборки нужно повторно вставить элемент
                // смодулировать вероятность на основе частоты выподения
                for (Toy toy : toys) {
                    for (int w = 0; w < toy.weight; w++) {
                        queue.add(Integer.valueOf(toy.id));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred while writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ToyStore store = new ToyStore();
        store.put("1 4 конструктор");
        store.put("2 3 робот");
        store.put("3 3 кукла");

        store.runDraw();
    }
}





  


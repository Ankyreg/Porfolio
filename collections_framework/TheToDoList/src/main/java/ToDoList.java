import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {
    private final ArrayList<String> toDo = new ArrayList<>();
    private boolean isActive = true;
    private final Scanner scanner;

    public ToDoList() {
        scanner = new Scanner(System.in);
    }

    public String showCommands() {
        return """
                "add" - to add a task\s
                "list" - to show all the tasks\s
                "edit" - to edit a task\s
                "delete" - to delete a task
                "exit" - to exit""";
    }

    /**
     *  expects to enter text with or without an index
     *  0/1/2... task text
     *  just task text
     */
    private void addTask() throws IllegalArgumentException {
        String task = scanner.nextLine().trim();
        if (task.isBlank()){
            throw new IllegalArgumentException("Wrong input");
        }
        if (task.matches("^\\d+.+")) {
            int index = Integer.parseInt(task.split("\\D")[0]);
            String text = task.substring(String.valueOf(index).length()).trim();
            toDo.add(Math.min(index, toDo.size()), text);
        } else if (task.matches("[0-9]+")) {
            System.out.println("Enter a task, please");
            String line = scanner.nextLine();
            toDo.add(Math.min(Integer.parseInt(task), toDo.size()), line);
        } else {
            toDo.add(task);
        }
        System.out.println("Done");
    }

    /**
     * changes the task index to a new one
     * @param index number of task
     */
    private void changeIndex(int index) {
        System.out.println("Enter new index of this task, please");
        String newIndex = scanner.nextLine();
        if (!newIndex.isBlank() && newIndex.matches("[0-9]+") && Integer.parseInt(newIndex) <= toDo.size() - 1) {
            String oldTask = toDo.get(index);
            toDo.remove(index);
            toDo.add(Integer.parseInt(newIndex), oldTask);
            System.out.println("Done!");
        } else {
            System.out.println("This is the wrong index. List contains only ".concat(String.valueOf(toDo.size())).concat(toDo.size() > 1 ? " tasks" : " task"));
            changeIndex(index);
        }
    }

    /**
     *  selects the editing option for the task. Changing the index or contents of a task
     * @param number phone number
     */
    private void editTaskOrIndex(String number) {
        int index = Integer.parseInt(number);
        System.out.println("If you want to change text, enter \"1\". if you want to change index, enter \"2\"");
        String num = scanner.nextLine();
        if (num.contains("1")) {
            changeTask(index);
        } else if (num.contains("2")) {
            changeIndex(index);
        } else {
            System.out.println("Only \"1\" or \"2\", please");
            editTaskOrIndex(number);
        }
    }

    /**
     * changes the task index to a new one
     * @param index number of a task
     */
    private void changeTask(int index) {
        System.out.println("Enter new text");
        String newText = scanner.nextLine();
        toDo.set(index, newText);
        System.out.println("Done!");
        showCommands();
    }

    /**
     * waits for the task index or exit command to the main menu
     */
    private void edit() throws Exception {
        if (toDo.isEmpty()) {
            throw new Exception("List is empty");
        }
        System.out.println("What task will be edited? Enter the number of a task, please");
        System.out.println("to exit to the main menu, press \"@\"");
        String number = scanner.nextLine();
        if (number.matches("@")) {
            System.out.println(showCommands());
            return;
        }
        if (number.matches("[0-9]+") && Integer.parseInt(number) <= toDo.size() - 1) {
            editTaskOrIndex(number);
        } else {
            throw new Exception("It requires only number. List contains "
                    .concat(String.valueOf(toDo.size())).concat(toDo.size() <= 1 ? " task" : " tasks"));
            }
    }

    /**
     * deleting a task or clearing a list
     */
    public void deleteTask() throws Exception {
        if (toDo.isEmpty()) {
            throw new Exception("List is empty");
        }
        System.out.println("What task should be removed? Enter index, please. to to exit to the main menu, press \"@\"");
        System.out.println("If you want to remove all the list, press \"/\" , please");
        String index = scanner.nextLine();
        if (index.matches("@")) {
            System.out.println(showCommands());
            return;
        }
        if (index.matches("/")) {
            toDo.clear();
            System.out.println("List is clear");
            return;
        }
        if (index.matches("[0-9]+") && Integer.parseInt(index) <= toDo.size() - 1) {
            toDo.remove(Integer.parseInt(index));
            System.out.println("Task is removed");
        } else {
            throw new Exception("Wrong command");
        }
    }

    /**
     *  shows the contents of the list
     */
    private void showList() throws Exception {
        if (toDo.isEmpty()) {
            throw new Exception("List is empty");
        }
        for (int i = 0; i < toDo.size(); i++) {
            System.out.println(i + " - ".concat(toDo.get(i)));
        }
    }

    public void start() {
        System.out.println(showCommands());
        while (isActive) {
            String command = scanner.nextLine();
            try {
                switch (command) {
                    case "add" -> {
                        System.out.println("Enter the task number or just the task text if you want to place the task at the end of the list");
                        addTask();
                    }
                    case "list" -> {
                        showList();
                    }
                    case "edit" -> edit();
                    case "delete" -> deleteTask();
                    case "exit" -> {
                        System.out.println("Bye!");
                        isActive = false;
                    }
                    default -> System.out.println(showCommands());
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}

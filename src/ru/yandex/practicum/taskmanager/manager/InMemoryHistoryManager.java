package ru.yandex.practicum.taskmanager.manager;

import ru.yandex.practicum.taskmanager.model.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{
    //protected Map<Integer, Node<Task>> map = new HashMap<>();
    /**
     * Pointer to first node.
     */
    Node<Task> first;
    /**
     * Pointer to last node.
     */
    Node<Task> last;

    /**
     * Добавляет Task в History
     * */
    @Override
    public void add(Task task) {
        linkLast(task);
    }

    /**
     * Отображает последние просмотренные пользователем задачи
     */
    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    /**
     * Удаляет задачу по номеру задачи
     * @param taskId - номер задачи
     */
    @Override
    public void remove(int taskId){
        if (map.size() > 0) {
            Node<Task> old = map.get(taskId);
            removeNode(old);
        }
    }


    void linkLast(Task e) {
        if (map.containsKey(e.getId())) {
            Node<Task> old = map.get(e.getId());
            removeNode(old);
        }
        addNode(e);
    }

    void addNode(Task e) {
        final Node<Task> l = last;
        final Node<Task> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        map.put(e.getId(), last);
    }


    List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node<Task> node = first;
        while(node != null) {
            tasks.add(node.item);
            node = node.next;
        }
        return tasks;
    }

    boolean removeNode(Node<Task> e) {
        if (e != null) {
            final Node<Task> prev = e.prev;
            final Node<Task> next = e.next;
            if (next != null) {
                next.prev = prev;
            } else {
                last = prev;
            }
            if (prev != null) {
                prev.next = next;
            } else {
                first = next;
            }
        }
        return true;
    }

    // Напишите статические методы static String toString(HistoryManager manager)
    public String toString(HistoryManager manager) {
        String str ="";
        Node<Task> first = null;
        for (Node<Task> node: map.values()) {
            if (node.prev == null) {
                first = node;
                break;
            }
        }
        if (first == null) {
            return str;
        }
        Node<Task> next = first;
        while (true) {
            if (str.equals("")) {
                str += next.item.getId();
            } else {
                str += "," + next.item.getId();
            }
            next = next.next;
            if (next == null) {
                break;
            }
        }
        return str;
    }
}

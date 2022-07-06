package ru.yandex.practicum.taskmanager.manager;

  class Node<Task> {
    Task item;
    Node<Task> next;
    Node<Task> prev;

    Node(Node<Task> prev, Task element, Node<Task> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}

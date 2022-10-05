package ru.yandex.taskTracker;

import ru.yandex.taskTracker.model.Task;

import java.util.*;

class InMemoryHistoryManager implements HistoryManager{
    private List<Task> taskHistory= new ArrayList<>();
    private Map<Integer,Node> nodeMap = new HashMap<>();
    private Node last;
    private Node first;
    List<Integer> pam = new LinkedList();



    @Override
    public void add(Task task) {
        if(nodeMap.size() == 10) {
            int id =first.task.getId();
            remove(id);
            nodeMap.remove(id);
        }
        if (nodeMap.containsKey(task.getId())){
            remove(task.getId());
            linkLast(task);
        }
        else {
            linkLast(task);
        }
        nodeMap.put(task.getId(), last);

    }

     @Override
     public void remove(int id) {
         Node node = nodeMap.get(id);
         nodeMap.remove(id);
         removeNode(node);
     }
     private void removeNode(Node node){
        if (node.prev == null){
            if(node.next !=null){
                (node.next).prev = null;
                first = node.next;
            }else{
                first = null;
            }

        }else if(node.next == null){
            (node.prev).next = null;
            node.prev= last;
        }else {
            (node.prev).next = node.next;
            (node.next).prev = node.prev;
        }
     }

     @Override
    public List<Task> getHistory() {

        return getTask();
    }

    private void linkLast(Task task){
        Node newNode = new Node(task,null,last);
            if(first==null) {
                first = newNode;
                last = newNode;
            } else{
                    last.next = newNode;
                    last= newNode;
                }
            }
    private List<Task> getTask(){
        List<Task> taskHistory = new ArrayList<>();
        Node node = first;
        while (node!=null){
            taskHistory.add(node.task);
            node = node.next;

        }
        return taskHistory;
    }
    }

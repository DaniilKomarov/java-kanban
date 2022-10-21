package ru.yandex.taskTracker;

import ru.yandex.taskTracker.model.Task;
import ru.yandex.taskTracker.Node;

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
            int id = first.getTask().getId();
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
        if (node.getPrev() == null){
            if(node.getNext() !=null){
                (node.getNext()).setPrev(null);
                first = node.getNext();
            }else{
                first = null;
            }

        }else if(node.getNext() == null){
            (node.getPrev()).setNext(null);
            node.setPrev(last);
        }else {
            (node.getPrev()).setNext(node.getNext());
            (node.getNext()).setPrev(node.getPrev());
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
                    last.setNext(newNode);
                    last= newNode;
                }
            }
    private List<Task> getTask(){
        List<Task> taskHistory = new ArrayList<>();
        Node node = first;
        while (node!=null){
            taskHistory.add(node.getTask());
            node = node.getNext();

        }
        return taskHistory;
    }
    }

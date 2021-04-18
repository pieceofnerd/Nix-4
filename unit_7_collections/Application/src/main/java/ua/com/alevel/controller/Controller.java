package ua.com.alevel.controller;

import ua.com.alevel.entity.User;
import ua.com.alevel.impl.OrderList;
import ua.com.alevel.impl.ShowLibraryImpl;
import ua.com.alevel.util.ConsoleUtil;
import ua.com.alevel.view.View;

import java.util.ArrayList;
import java.util.List;

public class Controller<Type extends Comparable<? super Type>> {
    private final ShowLibraryImpl<Type> showLibraryService = new ShowLibraryImpl<>();
    private boolean flag = true;

    public void showLibraryWithCustomObject(int role) {
        while (flag) {
            int option = View.chooseOption();
            switch (option) {
                case 0:
                    flag = false;
                    break;

                case 1: {
                    int size = getSize();
                    List<Type> list;
                    if (role == 1) {
                        list = (List<Type>) getList(size);
                    } else {
                        list = (List<Type>) getUsers(size);
                    }
                    showLibraryService.addAll(list);
                    ConsoleUtil.output(showLibraryService.getOrderList());
                    break;
                }

                case 2: {
                    Type element=getElement(role);
                    showLibraryService.add(element);
                    ConsoleUtil.output(showLibraryService.getOrderList());
                    break;
                }
                case 3: {
                    int index = getIndex();
                    if (isIndexPresent(index)) {
                        break;
                    }
                    Type obj = showLibraryService.get(index);
                    ConsoleUtil.output(obj);
                    break;
                }
                case 4: {
                    Type element = getElement(role);
                    if (isElementPresent(element)) {
                        break;
                    }
                    int index = showLibraryService.indexOf(element);
                    ConsoleUtil.output(index);
                    break;
                }
                case 5: {
                    Type element = getElement(role);
                    if (isElementPresent(element)) {
                        break;
                    }
                    int index = showLibraryService.lastIndexOf(element);
                    ConsoleUtil.output(index);
                    break;
                }
                case 6: {
                    Type element = getElement(role);
                    if (isElementPresent(element)) {
                        break;
                    }
                    showLibraryService.remove(element);
                    ConsoleUtil.output(showLibraryService.getOrderList());
                    break;
                }
                case 7: {
                    int index = getIndex();
                    if (isIndexPresent(index)) {
                        break;
                    }
                    Type element = getElement(role);
                    showLibraryService.set(index, element);
                    ConsoleUtil.output(showLibraryService.getOrderList());
                    break;
                }
                case 8: {
                    int firstIndex = getIndex();
                    int secondIndex = getIndex();
                    if (isIndexPresent(firstIndex) || isIndexPresent(secondIndex)) {
                        break;
                    }
                    if (secondIndex < firstIndex) {
                        System.out.println("first index must be less than second");
                        break;
                    }
                    OrderList<Type> sublist = (OrderList<Type>) showLibraryService.subList(firstIndex, secondIndex);
                    ConsoleUtil.output(sublist);
                    break;
                }
                case 9: {
                    showLibraryService.clean();
                    ConsoleUtil.output(showLibraryService.getOrderList());
                    break;
                }
            }
        }
    }

    private List<Integer> getList(int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            System.out.print("Please, enter a value: ");
            try {
                int value = ConsoleUtil.getSize();
                list.add(value);
            } catch (NumberFormatException e) {
                System.out.println("Your value doesn't correct ");
                return getList(size);
            }
        }
        return list;
    }


    private int getSize() {
        try {
            int size = ConsoleUtil.getSize();
            if (size < 0) {
                throw new NumberFormatException();
            }
            return size;
        } catch (NumberFormatException e) {
            System.out.println("Your value doesn't correct ");
            return getSize();
        }
    }

    private Integer getElement() {
        System.out.print("Please, enter an element: ");
        return ConsoleUtil.getNumber();
    }

    private boolean isElementPresent(Type element) {
        if (!showLibraryService.getOrderList().contains(element)) {
            System.out.println("There is no element  " + element);
            return true;
        } else return false;
    }

    private boolean isIndexPresent(int index) {
        if (index < 0 || index > showLibraryService.size() - 1) {
            System.out.println("There is no element with index " + index);
            return true;
        }
        return false;
    }

    private int getIndex() {
        System.out.print("Please, input index of element that you want to get access: ");
        return ConsoleUtil.getNumber();
    }

    private List<User> getUsers(int size) {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            System.out.print("Please, enter a name of " + (i + 1) + " user: ");
            try {
                String name = getName();
                System.out.print("Please, enter an age of " + (i + 1) + " user: ");
                int age = getAge();
                list.add(new User(name, age));
            } catch (NumberFormatException e) {
                System.out.println("Your value doesn't correct ");
                return getUsers(size);
            }
        }
        return list;
    }

    private String getName() throws NumberFormatException {
        String name = ConsoleUtil.readFromConsole();
        assert name != null;
        if (name.isBlank()) {
            System.out.print("Name cannot be blank");
            throw new NumberFormatException();
        }
        return name;
    }

    private int getAge() {
        int age = ConsoleUtil.getNumber();
        if (age < 0 || age > 100) {
            System.out.print("Age cannot be blank");
            throw new NumberFormatException();
        }
        return age;
    }

    private User getUser() {
        System.out.print("Please, enter an user: ");
        System.out.print("Please, enter a user name: ");
        String name = getName();
        System.out.print("Please, enter a user age: ");
        int age;
        try {
            age = getAge();
        } catch (NumberFormatException e) {
            System.out.println("Your value doesn't correct ");
            return getUser();
        }
        return new User(name, age);
    }

    private Type getElement(int role) {
        Type element;
        if (role == 1) {
            element = (Type) getElement();
        } else {
            element = (Type) getUser();
        }
        return element;
    }


}

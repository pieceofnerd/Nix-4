package ua.com.alevel.controller;

import ua.com.alevel.MathSet;
import ua.com.alevel.impl.ShowLibraryServiceImpl;
import ua.com.alevel.service.ShowLibraryService;
import ua.com.alevel.util.ConsoleUtil;

public class Controller<number extends Number & Comparable<number>> {
    private final ShowLibraryService<number> showLibraryService = new ShowLibraryServiceImpl<>();
    private boolean flag = true;

    public void showLibraryWithCustomObject(int role) {

        switch (role) {
            case 0:
                flag = false;
                break;

            case 1: {
                try {
                    showLibraryService.add(getElement());
                    ConsoleUtil.output(showLibraryService.getMathSet());
                } catch (NumberFormatException e) {
                    System.out.println("\nIncorrect number\n");
                }
                break;
            }

            case 2: {
                try {
                    int quantity = getSize();
                    Number[] numbers = new Number[quantity];
                    for (int i = 0; i < quantity; i++) {
                        numbers[i] = getElement();
                    }
                    showLibraryService.add((number[]) numbers);
                    ConsoleUtil.output(showLibraryService.getMathSet());
                }
            catch(NumberFormatException e){
                System.out.println("\nIncorrect number\n");
            }
            break;
        }
        case 3: {
            try{
            System.out.println("\nCreate MathSet by array ");

            int quantity = getSize();
            Number[] array = new Number[quantity];
            for (int i = 0; i < quantity; i++) {
                array[i] = getElement();
            }
            showLibraryService.join(new MathSet(array));
            ConsoleUtil.output(showLibraryService.getMathSet());
            } catch (NumberFormatException e) {
                System.out.println("\nIncorrect number\n");
            }
            break;
        }
        case 6: {
            int index = getIndex();
            if (index < 0 || index > showLibraryService.getSize() - 1) {
                System.out.println("\nYou wrote incorrect index");
                break;
            }
            number element = showLibraryService.get(index);
            ConsoleUtil.output(element);
            break;
        }
        case 4: {
            showLibraryService.sortDesc();
            ConsoleUtil.output(showLibraryService.getMathSet());
            break;
        }
        case 5: {
            showLibraryService.sortAsc();
            ConsoleUtil.output(showLibraryService.getMathSet());
            break;
        }
        case 7: {
            number min = showLibraryService.getMin();
            ConsoleUtil.output(min);
            break;
        }
        case 8: {
            number max = showLibraryService.getMax();
            ConsoleUtil.output(max);
            break;
        }
        case 9: {
            number average = showLibraryService.getAverage();
            ConsoleUtil.output(average);
            break;
        }
        case 10: {
            number median = showLibraryService.getMedian();
            ConsoleUtil.output(median);
            break;
        }
    }

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

    private number getElement() {
        System.out.print("Please, enter an element: ");
        return (number) ConsoleUtil.getNumber();
    }

    private int getIndex() {
        System.out.print("Please, input index of element that you want to get access: ");
        return (Integer) ConsoleUtil.getNumber();
    }

}

package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {
    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        Sums sum = new Sums();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                sum.setRowSum(sum.getRowSum() + matrix[i][j]);
                sum.setColSum(sum.getColSum() + matrix[j][i]);
            }
            result[i] = sum;
            sum = new Sums();
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = new Sums();
        }
        getRowSumTask(matrix, result);
        getColSumTask(matrix, result);
        return result;
    }

    public static void getRowSumTask(int[][] data, Sums[] sums) throws ExecutionException,
            InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length; j++) {
                    sums[i].setRowSum(sums[i].getRowSum() + data[i][j]);
                }
            }
            return sums;
        }).get();
    }

    public static void getColSumTask(int[][] data, Sums[] sums) throws ExecutionException,
            InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < data.length; i++) {
                for (int[] el : data) {
                    sums[i].setColSum(sums[i].getColSum() + el[i]);
                }
            }
            return sums;
        }).get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] arr = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] rslLinear = sum(arr);
        for (Sums el : rslLinear) {
            System.out.println(el.getRowSum());
            System.out.println(el.getColSum());
        }
        Sums[] rslAsync = asyncSum(arr);
        for (Sums el : rslAsync) {
            System.out.println(el.getRowSum());
            System.out.println(el.getColSum());
        }
    }
}

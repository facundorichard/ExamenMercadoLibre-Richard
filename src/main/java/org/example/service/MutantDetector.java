package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length == 0)
            return false;

        final int n = dna.length;
        // Optimización #1: Conversión a char[][] para acceso O(1)
        char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }

        int sequenceCount = 0;

        // Optimización #2: Pasada única (bucles anidados)
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Optimización #3: Verificación de límites dentro del bucle para evitar
                // verificaciones innecesarias

                // Verificar Horizontal
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                        // Optimización #0: Terminación Temprana
                        if (sequenceCount > 1)
                            return true;
                    }
                }

                // Verificar Vertical
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1)
                            return true;
                    }
                }

                // Verificar Diagonal (Descendente \ )
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalDesc(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1)
                            return true;
                    }
                }

                // Verificar Diagonal (Ascendente / )
                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalAsc(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1)
                            return true;
                    }
                }
            }
        }

        return false;
    }

    // Optimización #5: Comparación directa sin bucles
    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row][col + 1] == base &&
                matrix[row][col + 2] == base &&
                matrix[row][col + 3] == base;
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col] == base &&
                matrix[row + 2][col] == base &&
                matrix[row + 3][col] == base;
    }

    private boolean checkDiagonalDesc(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col + 1] == base &&
                matrix[row + 2][col + 2] == base &&
                matrix[row + 3][col + 3] == base;
    }

    private boolean checkDiagonalAsc(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row - 1][col + 1] == base &&
                matrix[row - 2][col + 2] == base &&
                matrix[row - 3][col + 3] == base;
    }
}

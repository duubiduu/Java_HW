class Matrix {
    int rows;
    int cols;
    Complex[][] data;


    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] = new Complex(0, 0);
            }
        }
    }

    public static Matrix readFromConsole(java.util.Scanner scanner, int matrix_num) {
        int rows = 0;
        int cols = 0;


        while (true) {
            try {
                System.out.print("введите количество строк для матрицы " + matrix_num + ": ");
                rows = Integer.parseInt(scanner.nextLine().trim());
                if (rows <= 0) {
                    System.out.println("Ошибка! Количество строк должно быть больше 0");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка формата! Введите целое число");
            }
        }
        while (true) {
            try {
                System.out.print("введите количество столбцов для матрицы " + matrix_num + ": ");
                cols = Integer.parseInt(scanner.nextLine().trim());
                if (cols <= 0) {
                    System.out.println("Ошибка! Количество столбцов должно быть больше 0");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка формата! Введите целое число.");
            }
        }
        Matrix matrix = new Matrix(rows, cols);
        System.out.println("\nЗаполнение матрицы " + matrix_num + " по элементам:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                while (true) {
                    try {
                        System.out.print("элемент [" + (i + 1) + "][" + (j + 1) + "]: ");
                        String input = scanner.nextLine();
                        matrix.data[i][j] = Complex.parse(input);
                        break;
                    } catch (Exception e) {
                        System.out.println("Ошибка формата! Неверный ввод комплексного числа. Повторите попытку.");
                    }
                }
            }
        }
        return matrix;
    }

    public void display() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String strValue = data[i][j].toString();
                System.out.printf("%-18s", strValue);
            }
            System.out.println();
        }
    }

    public Matrix add(Matrix other) {
        if (other == null) throw new IllegalArgumentException("Передана пустая матрица");
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Размеры матриц должны совпадать для сложения!");
        }
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j].add(other.data[i][j]);
            }
        }
        return result;
    }

    public Matrix multiply(Matrix other) {
        if (other == null) throw new IllegalArgumentException("Передана пустая матрица");
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Количество столбцов матрицы A должно быть равно количеству строк матрицы B!");
        }
        Matrix result = new Matrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.add(this.data[i][k].multiply(other.data[k][j]));
                }
                result.data[i][j] = sum;
            }
        }
        return result;
    }

    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[j][i] = this.data[i][j];
            }
        }
        return result;
    }

    public Complex determinant() {
        if (rows != cols) throw new IllegalArgumentException("Матрица не квадратная! Посчитать определитель невозможно!");
        int n = rows;
        Complex[][] a = new Complex[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(this.data[i], 0, a[i], 0, n);
        }
        Complex det = new Complex(1, 0);
        for (int i = 0; i < n; i++) {
            int pivot = i;
            for (int r = i + 1; r < n; r++) {
                if (a[r][i].abs() > a[pivot][i].abs()) pivot = r;
            }
            if (pivot != i) {
                Complex[] temp = a[i]; a[i] = a[pivot]; a[pivot] = temp;
                det = det.multiply(new Complex(-1, 0));
            }
            if (a[i][i].abs() < 1e-9) return new Complex(0, 0);
            det = det.multiply(a[i][i]);
            for (int r = i + 1; r < n; r++) {
                Complex factor = a[r][i].divide(a[i][i]);
                for (int c = i; c < n; c++) {
                    a[r][c] = a[r][c].subtract(factor.multiply(a[i][c]));
                }
            }
        }
        return det;
    }

    public Matrix invert() {
        if (rows != cols) throw new IllegalArgumentException("Матрица не квадратная! Посчитать обратную для нее невозможно!");
        int n = rows;
        Complex[][] a = new Complex[n][2 * n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(this.data[i], 0, a[i], 0, n);
            for (int j = n; j < 2 * n; j++) {
                a[i][j] = (i == (j - n)) ? new Complex(1, 0) : new Complex(0, 0);
            }
        }
        for (int i = 0; i < n; i++) {
            int pivot = i;
            for (int r = i + 1; r < n; r++) {
                if (a[r][i].abs() > a[pivot][i].abs()) pivot = r;
            }
            if (pivot != i) { Complex[] temp = a[i]; a[i] = a[pivot]; a[pivot] = temp; }

            if (a[i][i].abs() < 1e-9) {
                throw new ArithmeticException("Матрица вырождена (det = 0), обратной матрицы не существует.");
            }
            Complex divisor = a[i][i];
            for (int c = i; c < 2 * n; c++) {
                a[i][c] = a[i][c].divide(divisor);
            }
            for (int r = 0; r < n; r++) {
                if (r != i) {
                    Complex factor = a[r][i];
                    for (int c = i; c < 2 * n; c++) {
                        a[r][c] = a[r][c].subtract(factor.multiply(a[i][c]));
                    }
                }
            }
        }
        Matrix inv = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            System.arraycopy(a[i], n, inv.data[i], 0, n);
        }
        return inv;
    }

    public Matrix divide(Matrix other) {
        if (other == null) throw new IllegalArgumentException("Передана пустая матрица");
        return this.multiply(other.invert());
    }
}
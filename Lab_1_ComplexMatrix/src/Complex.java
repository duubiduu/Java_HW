class Complex {
    double real;
    double imaginary;


    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public static Complex parse(String complex_string) {
        if (complex_string == null) {
            throw new IllegalArgumentException("Комплексное число не может быть null");
        }
        complex_string = complex_string.trim().replaceAll("\\s+", "").toLowerCase();
        if (complex_string.isEmpty()) {
            throw new IllegalArgumentException("Строка пустая");
        }

        switch (complex_string) {
            case "0" -> {return new Complex(0, 0);}
            case "i", "+i" -> {return new Complex(0, 1);}
            case "-i" -> {return new Complex(0, -1);}
        }

        if (complex_string.contains("i")) {
            if (!complex_string.endsWith("i")) {
                throw new IllegalArgumentException("Буква i должна быть в конце мнимой части");
            }
            String withoutI = complex_string.replace("i", "");

            int sign_ind = 0;
            if (withoutI.lastIndexOf('+') > withoutI.lastIndexOf('-')) {
                sign_ind = withoutI.lastIndexOf('+');
            } else {
                sign_ind = withoutI.lastIndexOf('-');
            }

            if (sign_ind <= 0) {
                if (withoutI.equals("-")) return new Complex(0, -1);
                if (withoutI.equals("+") || withoutI.isEmpty()) return new Complex(0, 1);
                return new Complex(0, Double.parseDouble(withoutI));
            } else {
                String realPartStr = withoutI.substring(0, sign_ind);
                String imgStr = withoutI.substring(sign_ind);
                double new_real = Double.parseDouble(realPartStr);
                double new_imaginary = 0.0;
                if (imgStr.equals("-")) new_imaginary = -1;
                else if (imgStr.equals("+")) new_imaginary = 1;
                else new_imaginary = Double.parseDouble(imgStr);
                return new Complex(new_real, new_imaginary);
            }
        } else {
            return new Complex(Double.parseDouble(complex_string), 0);
        }
    }

    public Complex add(Complex other) {
        if (other == null) {
            throw new IllegalArgumentException("Передан пустой объект");
        }
        return new Complex(this.real + other.real, this.imaginary + other.imaginary);
    }

    public Complex subtract(Complex other) {
        if (other == null) {
            throw new IllegalArgumentException("Передан пустой объект");
        }
        return new Complex(this.real - other.real, this.imaginary - other.imaginary);
    }

    public Complex multiply(Complex other) {
        if (other == null) {
            throw new IllegalArgumentException("Передан пустой объект");
        }
        double new_real = this.real * other.real - this.imaginary * other.imaginary;
        double new_imaginary = this.real * other.imaginary + this.imaginary * other.real;
        return new Complex(new_real, new_imaginary);
    }

    public Complex divide(Complex other) {
        if (other == null) {
            throw new IllegalArgumentException("Передан пустой объект");
        }
        double denominator = other.real * other.real + other.imaginary * other.imaginary;
        if (denominator == 0.0) {
            throw new ArithmeticException("Деление на ноль невозможно");
        }
        double new_real = (this.real * other.real + this.imaginary * other.imaginary) / denominator;
        double new_imaginary = (this.imaginary * other.real - this.real * other.imaginary) / denominator;
        return new Complex(new_real, new_imaginary);
    }

    public double abs() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    @Override
    public String toString() {
        if (imaginary == 0.0) {
            return String.format("%.2f", real);
        }
        if (real == 0.0) {
            return String.format("%.2fi", imaginary);
        }
        if (imaginary > 0) {
            return String.format("%.2f + %.2fi", real, imaginary);
        } else {
            return String.format("%.2f %.2fi", real, imaginary);
        }
    }
}
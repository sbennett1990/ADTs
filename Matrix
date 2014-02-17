/**
 * Class for matrix creation and manipulation.
 * Currently this class creates matrices of doubles.
 */
public class Matrix {
    private int m;  // rows
    private int n;  // columns
    private double[][] M;  // matrix M
    
    /**
     * Constructor. 
     * All Matrices must specify dimensions.
     * 
     * @param rows Number of rows
     * @param columns Number of columns
     */
    public Matrix(int rows, int columns) {
        setRows(rows);
        setColumns(columns);
        M = new double[m][n];
    }
    
    /**
     * Set number of rows.
     * 
     * @param rows 
     */
    private void setRows(int rows) {
        m = rows;
    }
    
    /**
     * Set number of columns
     * 
     * @param columns 
     */
    private void setColumns(int columns) {
        n = columns;
    }
    
    /**
     * Set the data at a specific location.
     * 
     * @param row The row of the data
     * @param column The column of the data
     * @param element Value to place at (row,column)
     */
    public void setData(int row, int column, double element) {
        if (row < m && column < n && row >= 0 && column >= 0) {
            M[row][column] = element;
        } else {
            throw new IndexOutOfBoundsException
                    ("That is outside the bounds of the matrix.");
        }
    }
    
    /**
     * Set all the data in the matrix to the value of element.
     * 
     * @param element Value to set all the data
     */
    public void setAllData(double element) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                M[i][j] = element;
            }
        }
    }
    
    /**
     * Return the number of rows in the matrix.
     * 
     * @return The number of rows
     */
    public int getRows() {
        return m;
    }
    
    /**
     * Return the number of columns in the matrix.
     * 
     * @return The number of columns
     */
    public int getColumns() {
        return n;
    }
    
    /**
     * Get the element at the specified row and column.
     * Numbering starts at 0. For a 2x2 matrix, the coordinates would be:
     * [(0,0), (0,1)]
     * [(1,0), (1,1)]
     * The range for a given position is: ( [0..row),[0..column) ).
     * 
     * @param row The row of the element
     * @param column The column of the element
     * @return Element at (row,column)
     */
    public double getElement(int row, int column) {
        //return M[row][column];
        
        if (row < m && column < n && row >= 0 && column >= 0) {
            return M[row][column];
        } else {
            throw new IndexOutOfBoundsException
                    ("That is outside the bounds of the matrix.");
        }
        
    }
    
    /**
     * Determines if this matrix is empty (ie, has 0 rows OR 0 columns).
     * 
     * @return True if the matrix is empty
     */
    public boolean isEmpty() {
        if (getRows() <= 0 || getColumns() <= 0) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Determine if this matrix is a square matrix (the number
     * of rows equals the number of columns).
     * 
     * @return True if matrix is square
     */
    public boolean isSquare() {
        return (m == n);
    }
    
    /**
     * Print out the matrix in an attractive and logical manner.
     * 
     * @param A a Matrix object
     */
    public static void printMatrix(Matrix A) {
        int i,j;
        
        if (!A.isEmpty()) {  // matrix is not empty
            
            System.out.println();
            for (i = 0; i < A.getRows(); i++) {
                
                System.out.print("[");
                for (j = 0; j < (A.getColumns() - 1); j++) {
                    System.out.print(A.getElement(i, j) + ",   ");
                }
                
                System.out.print(A.getElement(i, j) + "]\n");
            }
        } else {
            System.out.println("No Vertices");
        }
    }
    
    /**
     * Add matrix A to matrix B using standard matrix addition.
     * 
     * @param A a Matrix object
     * @param B a Matrix object
     * @return The result of A + B
     */
    public static Matrix add(Matrix A, Matrix B) {
        Matrix C;
        double sum = 0.0;
        
        if (A.getRows() == B.getRows() && A.getColumns() == B.getColumns()) {
            C = new Matrix(A.getRows(), A.getColumns());
            
            for (int i = 0; i < A.getRows(); i++) {
                for (int j = 0; j < A.getColumns(); j++) {
                    sum = A.getElement(i, j) + B.getElement(i, j);
                    C.setData(i, j, sum);
                }
            }
            
            return C;
        } else {
            System.out.println("These matrices are incompatible to add.");
            return null;
        }
    }
    
    /**
     * Subtract matrix B from matrix A (A - B) using standard matrix 
     * subtraction.
     * 
     * @param A a Matrix object
     * @param B a Matrix object
     * @return The result of A - B
     */
    public static Matrix subtract(Matrix A, Matrix B) {
        Matrix C;
        double diff = 0.0;
        
        if (A.getRows() == B.getRows() && A.getColumns() == B.getColumns()) {
            C = new Matrix(A.getRows(), A.getColumns());
            
            for (int i = 0; i < A.getRows(); i++) {
                for (int j = 0; j < A.getColumns(); j++) {
                    diff = A.getElement(i, j) - B.getElement(i, j);
                    C.setData(i, j, diff);
                }
            }
            
            return C;
        } else {
            System.out.println("These matrices are incompatible to subtract.");
            return null;
        }
    }
    
    /**
     * Multiply matrix A by a scalar using standard matrix scalar
     * multiplication.
     * 
     * @param scalar A scalar
     * @param A a Matrix object
     * @return The product of scalar * A
     */
    public static Matrix multiply(double scalar, Matrix A) {
        Matrix B = new Matrix(A.getRows(), A.getColumns());
        double product;
        
        for (int i = 0; i < A.getRows(); i++) {
            for (int j = 0; j < A.getColumns(); j++) {
                product = scalar * A.getElement(i, j);
                B.setData(i, j, product);
            }
        }
            
        return B;
    }
    
    /**
     * Multiply matrix A by another matrix B using standard matrix
     * multiplication. The number of columns in A must equal the
     * number of rows in B in order to multiply the two.
     * 
     * @param A a Matrix object
     * @param B a Matrix object
     * @return The result of A * B
     */
    public static Matrix multiply(Matrix A, Matrix B) {
        Matrix AB;
        double sum = 0.0;
        
        if (A.getColumns() == B.getRows()) {
            AB = new Matrix(A.getRows(), B.getColumns());
            
            for (int i = 0; i < A.getRows(); i++) {
                for (int j = 0; j < B.getColumns(); j++) {
                    for (int k = 0; k < B.getRows(); k++) {
                        sum += A.getElement(i, k) * B.getElement(k, j);
                    }
                    
                    AB.setData(i, j, sum);
                    sum = 0.0;
                }
            }
            
            return AB;
            
        } else {
            System.out.println("These matrices are incompatible to multiply.");
            return null;
        }
    }
}


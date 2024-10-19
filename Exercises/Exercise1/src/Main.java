import java.util.Random;

class Student {
    String name;
    int[] quizScores;


    public Student(String name) {
        this.name = name;
        this.quizScores = new int[15];
        generateQuizScores();
    }


    private void generateQuizScores() {
        Random random = new Random();
        for (int i = 0; i < quizScores.length; i++) {
            quizScores[i] = random.nextInt(101);
        }
    }


    public double getAverageQuizScore() {
        int total = 0;
        for (int score : quizScores) {
            total += score;
        }
        return total / (double) quizScores.length;
    }


    public void printQuizScoresInAscendingOrder() {

        int[] scoresCopy = quizScores.clone();

        System.out.print(name + " quiz scores in ascending order: ");
        for (int i = 0; i < scoresCopy.length; i++) {
            int minIndex = findMinimumIndex(scoresCopy);
            System.out.print(scoresCopy[minIndex] + " ");
            scoresCopy[minIndex] = Integer.MAX_VALUE;
        }
        System.out.println();
    }


    private int findMinimumIndex(int[] scores) {
        int minIndex = 0;
        for (int i = 1; i < scores.length; i++) {
            if (scores[i] < scores[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex;
    }
}

class PartTimeStudent extends Student {

    public PartTimeStudent(String name) {
        super(name);
    }
}

class FullTimeStudent extends Student {
    int[] examScores;


    public FullTimeStudent(String name) {
        super(name);
        examScores = new int[2];
        generateExamScores();
    }


    private void generateExamScores() {
        Random random = new Random();
        for (int i = 0; i < examScores.length; i++) {
            examScores[i] = random.nextInt(101);
        }
    }


    public void printExamScores() {
        System.out.print(name + " exam scores: ");
        for (int score : examScores) {
            System.out.print(score + " ");
        }
        System.out.println();
    }
}

class Session {
    Student[] students;
    int studentCount;


    public Session() {
        students = new Student[20];  // Room for 20 students
        studentCount = 0;
    }


    public void addStudent(Student student) {
        if (studentCount < students.length) {
            students[studentCount] = student;
            studentCount++;
        }
    }


    public void printAverageQuizScores() {
        System.out.println("Average quiz scores:");
        for (int i = 0; i < studentCount; i++) {
            System.out.println(students[i].name + ": " + students[i].getAverageQuizScore());
        }
    }


    public void printQuizScoresInAscendingOrder() {
        for (int i = 0; i < studentCount; i++) {
            students[i].printQuizScoresInAscendingOrder();
        }
    }


    public void printPartTimeStudents() {
        System.out.println("Part-time students:");
        for (int i = 0; i < studentCount; i++) {
            if (students[i] instanceof PartTimeStudent) {
                System.out.println(students[i].name);
            }
        }
    }


    public void printFullTimeExamScores() {
        System.out.println("Full-time students' exam scores:");
        for (int i = 0; i < studentCount; i++) {
            if (students[i] instanceof FullTimeStudent) {
                ((FullTimeStudent) students[i]).printExamScores();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Session session = new Session();  // Create a session

        for (int i = 1; i <= 10; i++) {
            session.addStudent(new PartTimeStudent("PartTimeStudent" + i));
        }


        for (int i = 1; i <= 10; i++) {
            session.addStudent(new FullTimeStudent("FullTimeStudent" + i));
        }


        session.printAverageQuizScores();
        session.printQuizScoresInAscendingOrder();
        session.printPartTimeStudents();
        session.printFullTimeExamScores();
    }
}

package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.io.PassReader;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;

public class StudyCafeApplication {

    public static void main(String[] args) {
        PassReader passReader = new StudyCafeFileHandler();

        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(passReader);
        studyCafePassMachine.run();
    }

}

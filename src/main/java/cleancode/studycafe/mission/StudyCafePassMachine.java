package cleancode.studycafe.mission;

import cleancode.studycafe.mission.exception.AppException;
import cleancode.studycafe.mission.io.InputHandler;
import cleancode.studycafe.mission.io.OutputHandler;
import cleancode.studycafe.mission.io.StudyCafeFileHandler;
import cleancode.studycafe.mission.model.StudyCafeLockerPass;
import cleancode.studycafe.mission.model.StudyCafePass;
import cleancode.studycafe.mission.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
    public void run() {

        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();


            //기간에 따라 패스 종류 보여주기, 패스 입력받기
            List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
            List<StudyCafePass> availablePasses = studyCafePasses.stream()
                    .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
                    .toList();
            outputHandler.showPassListForSelection(availablePasses);
            StudyCafePass selectedPass = inputHandler.getSelectPass(availablePasses);

            StudyCafeLockerPass lockerPass = showAndSelectLockerPass(selectedPass);

            outputHandler.showPassOrderSummary(selectedPass, lockerPass);


        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafeLockerPass showAndSelectLockerPass(StudyCafePass selectedPass) {

        //패스가 hourly나 weekly인경우 락커 이용 불가 락커 패스는 무조건 null반환
        if(selectedPass.getPassType() == StudyCafePassType.HOURLY || selectedPass.getPassType() == StudyCafePassType.WEEKLY) {
            return null;
        }

        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
        StudyCafeLockerPass availableLockerPass = lockerPasses.stream()
                .filter(option ->
                        option.getPassType() == selectedPass.getPassType() //고정석인지
                                && option.getDuration() == selectedPass.getDuration() //기간 옵션
                )
                .findFirst() //기간에 따른 락커 패스 찾기
                .orElse(null);

        //어떤 락커 패스 이용하는지, 혹은 이용하지 않는지
        if (availableLockerPass != null) {
            outputHandler.askLockerPass(availableLockerPass); //현재 락커 패스 사용할 것인지
            boolean isLockerSelected = inputHandler.getLockerSelection(); //사용자 입력받기

            if (isLockerSelected) {
                return availableLockerPass;
            }
        }
        return availableLockerPass;

    }

}

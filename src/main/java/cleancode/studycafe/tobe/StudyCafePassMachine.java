package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            StudyCafePass selectedPass = selectPass();

            Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass(selectedPass);

            //Optional 객체에 대해 값이 있을 경우 꺼내서 사용
            optionalLockerPass.ifPresentOrElse( //consumer타입, runnable타입 파라미터 필요
                    lockerPass -> outputHandler.showPassOrderSummary(selectedPass, lockerPass),
                    () -> outputHandler.showPassOrderSummary(selectedPass)
            );

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    //이용할 패스 정하는 메서드
    private StudyCafePass selectPass() {
        outputHandler.askPassTypeSelection();
        StudyCafePassType passType = inputHandler.getPassTypeSelectingUserAction();

        List<StudyCafePass> passCandidates = findPassCandidatesBy(passType);

        outputHandler.showPassListForSelection(passCandidates);
        return inputHandler.getSelectPass(passCandidates);
    }

    //패스 선택 후 가능한 이용권들만 리턴하는 메서드
    private List<StudyCafePass> findPassCandidatesBy(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> allPasses = studyCafeFileHandler.readStudyCafePasses();

        return allPasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
                .toList();
    }

    //Fixed패스 경우만 사물함 이용권 정하는 메서드
    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafePass selectedPass) {
        if(selectedPass.getPassType() != StudyCafePassType.FIXED){
            return Optional.empty();
        }

        StudyCafeLockerPass lockerPassCandidate = findLockerPassCandidateBy(selectedPass);

        if (lockerPassCandidate != null) {
            outputHandler.askLockerPass(lockerPassCandidate);
            //바로 사물함 이용권이 선택되었다는 플래그변수를 사용
            boolean isLockerSelected = inputHandler.getLockerSelection();

            if(isLockerSelected) {
                return Optional.of(lockerPassCandidate); //Optional로 감싸서 넘긴다.
            }
        }
        return Optional.empty();
    }

    //Fixed패스, 사물함 이용 선택 후 가능한 이용권만 리턴하는 메서드
    private StudyCafeLockerPass findLockerPassCandidateBy(StudyCafePass pass) {
        List<StudyCafeLockerPass> allLockerPasses = studyCafeFileHandler.readLockerPasses();

        return allLockerPasses.stream()
            .filter(lockerPass ->
                    lockerPass.getPassType() == pass.getPassType()
                            && lockerPass.getDuration() == pass.getDuration()
            )
            .findFirst()
            .orElse(null);
    }

}

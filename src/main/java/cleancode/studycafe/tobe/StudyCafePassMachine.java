package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.io.StudyCafeIOHandler;
import cleancode.studycafe.tobe.model.*;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafePass selectedPass = selectPass();

            Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass(selectedPass);

            //Optional 객체에 대해 값이 있을 경우 꺼내서 사용
            optionalLockerPass.ifPresentOrElse( //consumer타입, runnable타입 파라미터 필요
                    lockerPass -> ioHandler.showPassOrderSummary(selectedPass, lockerPass),
                    () -> ioHandler.showPassOrderSummary(selectedPass)
            );

        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    //이용할 패스 정하는 메서드
    private StudyCafePass selectPass() {
        StudyCafePassType passType = ioHandler.askPassTypeSelecting();
        List<StudyCafePass> passCandidates = findPassCandidatesBy(passType);

        return ioHandler.askPassSelecting(passCandidates);
    }

    //패스 선택 후 가능한 이용권들만 리턴하는 메서드
    private List<StudyCafePass> findPassCandidatesBy(StudyCafePassType studyCafePassType) {
        StudyCafePasses allPasses = studyCafeFileHandler.readStudyCafePasses();
        return allPasses.findPassBy(studyCafePassType);
    }

    //Fixed패스 경우만 사물함 이용권 정하는 메서드
    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafePass selectedPass) {
        //Fixed 패스가 아닌가? -> 사물함 옵션을 사용할 수 있는 타입이 아닌가? (필요한 관심사)
        if(selectedPass.cannotUseLocker()){
            return Optional.empty();
        }

        StudyCafeLockerPass lockerPassCandidate = findLockerPassCandidateBy(selectedPass);

        if (lockerPassCandidate != null) {
            boolean isLockerSelected = ioHandler.askLockerPassSelecting(lockerPassCandidate);
            if(isLockerSelected) {
                return Optional.of(lockerPassCandidate); //Optional로 감싸서 넘긴다.
            }
        }
        return Optional.empty();
    }

    //Fixed패스, 사물함 이용 선택 후 가능한 이용권만 리턴하는 메서드
    private StudyCafeLockerPass findLockerPassCandidateBy(StudyCafePass pass) {
        StudyCafeLockerPasses allLockerPasses = studyCafeFileHandler.readLockerPasses();
        return allLockerPasses.findLockerPassBy(pass);
    }

}

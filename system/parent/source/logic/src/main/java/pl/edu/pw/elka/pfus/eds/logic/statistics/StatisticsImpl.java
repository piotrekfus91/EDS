package pl.edu.pw.elka.pfus.eds.logic.statistics;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.dto.DocumentsNumberInDaysDto;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class StatisticsImpl implements Statistics {
    private Context context;
    private SecurityFacade securityFacade;
    private DocumentDao documentDao;

    public StatisticsImpl(Context context, SecurityFacade securityFacade, DocumentDao documentDao) {
        this.context = context;
        this.securityFacade = securityFacade;
        this.documentDao = documentDao;
    }

    @Override
    public List<DocumentsNumberInDaysDto> documentsUploadedInLastDays(int days) {
        User currentUser = securityFacade.getCurrentUser(context);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -days);
        Date fromDate = calendar.getTime();

        List<DocumentsNumberInDaysDto> result = documentDao
                .findDocumentsNumberUploadRecently(currentUser.getId(), fromDate);
        fulfillWithMissingDays(result, days);
        Collections.sort(result);
        return result;
    }

    private void fulfillWithMissingDays(List<DocumentsNumberInDaysDto> list, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getFirstDay(list));
        for(int i = 1; i < days; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date day = calendar.getTime();
            if(!isDayOnList(list, day)) {
                addMissingDay(list, day);
            }
        }
    }

    private Date getFirstDay(List<DocumentsNumberInDaysDto> list) {
        return Collections.min(list).getDay();
    }

    private boolean isDayOnList(List<DocumentsNumberInDaysDto> list, Date day) {
        for(DocumentsNumberInDaysDto dto : list) {
            if(dto.getDay().equals(day))
                return true;
        }
        return false;
    }

    private void addMissingDay(List<DocumentsNumberInDaysDto> list, Date day) {
        DocumentsNumberInDaysDto dto = new DocumentsNumberInDaysDto(day);
        list.add(dto);
    }
}

package pl.net.bluesoft.lot.casemanagement.dao;

import org.hibernate.Session;
import pl.net.bluesoft.lot.casemanagement.model.Case;
import pl.net.bluesoft.lot.casemanagement.model.CaseStage;
import pl.net.bluesoft.lot.casemanagement.model.CaseStateDefinition;
import pl.net.bluesoft.rnd.processtool.hibernate.SimpleHibernateBean;

import java.util.Date;
import java.util.Map;

/**
 * Created by pkuciapski on 2014-04-22.
 */
public class CaseDAOImpl extends SimpleHibernateBean<Case> implements CaseDAO {
    private CaseDefinitionDAO caseDefinitionDAO;
    private CaseStateDefinitionDAO caseStateDefinitionDAO;
    private CaseStageDAO caseStageDAO;

    public CaseDAOImpl(final Session session) {
        super(session);
    }

    public CaseDAOImpl(final Session session, final CaseDefinitionDAO caseDefinitionDAO, final CaseStateDefinitionDAO caseStateDefinitionDAO, final CaseStageDAO caseStageDAO) {
        this(session);
        this.caseDefinitionDAO = caseDefinitionDAO;
        this.caseStateDefinitionDAO = caseStateDefinitionDAO;
        this.caseStageDAO = caseStageDAO;
    }

    @Override
    public Case createCase(long caseDefinitionId, String name, String number, long caseStateDefinitionId, Map<String, String> simpleAttributes) {
        final Case newCase = new Case();
        newCase.setName(name);
        newCase.setNumber(number);
        newCase.setCreateDate(new Date());
        newCase.setDefinition(caseDefinitionDAO.getDefinitionById(caseDefinitionId));
        final CaseStateDefinition caseStateDefinition = caseStateDefinitionDAO.getStateDefinitionById(caseStateDefinitionId);
        final CaseStage initialStage = new CaseStage();
        initialStage.setStartDate(new Date());
        initialStage.setCaseStateDefinition(caseStateDefinition);
        initialStage.setCase(newCase);
        initialStage.setName(caseStateDefinition.getName());
        newCase.setCurrentStage(initialStage);
        newCase.getStages().add(initialStage);
        saveOrUpdate(newCase);
        return newCase;
    }

    @Override
    public Case getCaseById(long caseId) {
        return (Case) this.session.get(Case.class, caseId);
    }
}
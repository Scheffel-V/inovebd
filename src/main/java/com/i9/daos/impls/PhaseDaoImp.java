package com.i9.daos.impls;

import com.i9.daos.BaseDao;
import com.i9.daos.EmployeeHoursPerDayDao;
import com.i9.daos.PhaseDao;
import com.i9.models.EmployeeHoursPerDay;
import com.i9.models.Phase;
import org.springframework.beans.factory.annotation.Required;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhaseDaoImp implements PhaseDao{

    private BaseDao baseDao;
    private EmployeeHoursPerDayDao employeeHoursPerDayDao;

    @Override
    public Phase getPhase(int id) {
        Phase phase = new Phase();
        ResultSet resultSet = null;
        try {
            resultSet = baseDao.searchQuery("SELECT * FROM Phase AS x WHERE x.id = " + String.valueOf(id) + ";");
            resultSet.next();
            phase.setId(id);
            phase.setNumber(resultSet.getInt("number"));
            phase.setProjectId(resultSet.getInt("projectId"));
            phase.setObservation(resultSet.getString("observation"));
            phase.setInitialDate(resultSet.getDate("initialDate"));
            phase.setEndDate(resultSet.getDate("endDate"));
            phase.setHourEstimation(resultSet.getInt("hourEstimation"));

            List<EmployeeHoursPerDay> employeesHoursPerDay = employeeHoursPerDayDao.getEmployeesHoursPerDayByPhase(id);
            phase.setEmployeesHoursPerDay(employeesHoursPerDay);

        } catch (SQLException e){
            System.out.println("Error while searching on project Table");
            e.printStackTrace();
        }
        finally {
            baseDao.closeQuery(resultSet);
        }

        return phase;
    }

    @Override
    public List<Phase> getPhaseByProject(int projectId) {
        List<Phase> phases = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = baseDao.searchQuery("SELECT * FROM Phase AS x WHERE x.projectId = " + String.valueOf(projectId) + ";");
            while(resultSet.next()) {
                Phase phase = new Phase();
                int id = resultSet.getInt("id");
                phase.setId(id);
                phase.setNumber(resultSet.getInt("number"));
                phase.setProjectId(projectId);
                phase.setObservation(resultSet.getString("observation"));
                phase.setInitialDate(resultSet.getDate("initialDate"));
                phase.setEndDate(resultSet.getDate("endDate"));
                phase.setHourEstimation(resultSet.getInt("hourEstimation"));

                List<EmployeeHoursPerDay> employeesHoursPerDay = employeeHoursPerDayDao.getEmployeesHoursPerDayByPhase(id);
                phase.setEmployeesHoursPerDay(employeesHoursPerDay);

                phases.add(phase);
            }

        } catch (SQLException e){
            System.out.println("Error while searching on project Table");
            e.printStackTrace();
        }
        finally {
            baseDao.closeQuery(resultSet);
        }

        return phases;
    }

    @Override
    public List<Phase> getAll() {
        return null;
    }

    @Override
    public boolean save(Phase object) {
        return false;
    }

    @Override
    public boolean delete(Phase object) {
        return false;
    }

    @Override
    public boolean update(Phase object) {
        return false;
    }

    @Required
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Required
    public void setEmployeeHoursPerDayDao(EmployeeHoursPerDayDao employeeHoursPerDayDao) {
        this.employeeHoursPerDayDao = employeeHoursPerDayDao;
    }
}

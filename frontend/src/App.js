import React, {Component} from "react";
import {Route, Switch, Redirect, BrowserRouter} from 'react-router-dom';
import authService from "./services/auth-service";
import routeService from "./services/route-service";
import "./App.css";

class App extends Component {
  
  render() {
    
    return (
        <>
          <BrowserRouter basename="/clinic" exact>
              <Switch>
                  {routeService.getAllowedRoutes().map(route => {
                      if(route.hasOwnProperty("component"))
                          return <Route path={route.path} component={route.component} exact/>
                      else
                          return <Route path={route.path} render={() => route.render} exact/>
                  })}
                  {routeService.getRedirect()}
              </Switch>
            
            {/*<Route path="/predefinedExaminations" component={PredefinedExaminationPage} exact/>*/}
            {/*<Route path="/recipes" component={RecipeCertificationPage} exact/>*/}
            {/*<Route path="/patients" component={PatientsSearchPage} exact/>*/}
            {/*<Route path="/patients/create" render={() => <CreateUpdatePatientPage creating/>}/>*/}
            {/*<Route path="/patients/update/:jmbg" render={() => <CreateUpdatePatientPage creating={false}/>}/>*/}
            {/*<Route path="/nurses/create" render={() => <CreateUpdateNursePage creating/>} exact/>*/}
            {/*<Route path="/nurses/update/:jmbg" render={() => <CreateUpdateNursePage creating={false}/>} exact/>*/}
            {/*<Route path="/clinic-reports" component={ClinicReportPage} exact/>*/}
            {/*<Route path="/clinics" component={ClinicsSearchPage} exact/>*/}
            {/*<Route path="/work-calendar" component={WorkCalendarPage} exact/>*/}
            {/*<Route path="/work-calendar/:jmbg" component={WorkCalendarPage} exact/>*/}
            {/*<Route path="/examinations/:id" component={ExaminationPage} exact/>*/}
            {/*<Route path="/medical-records/:jmbg" component={MedicalRecordPage} exact/>*/}
            {/*<Route path="/users/requests" component={RegistrationRequestPage} exact/>*/}
            {/*<Route path="/doctors/create" render={() => <CreateUpdateDoctorsPage creating/>}/>*/}
            {/*<Route path="/doctors/update/:jmbg" render={() => <CreateUpdateDoctorsPage creating={false}/>}/>*/}
            {/*<Route path="/doctors/evaluate/:jmbg" render={() => <EvaluateDoctorPage/>}/>*/}
            {/*<Route path="/clinic-administrators/create" render={() => <CreateUpdateAdministratorPage creating clinicAdmin/>} exact/>*/}
            {/*<Route path="/clinic-administrators/update/:id" render={() => (<CreateUpdateAdministratorPage creating={false} clinicAdmin/>             )} exact/>*/}
            {/*<Route path="/clinic-centre-administrators/create" render={() => (<CreateUpdateAdministratorPage creating clinicAdmin={false}/>)} exact/>*/}
            {/*<Route path="/clinic-centre-administrators/update/:id" render={() => (<CreateUpdateAdministratorPage creating={false} clinicAdmin={false}/>)} exact/>*/}
            {/*<Route path="/clinics/create" render={() => <CreateUpdateClinicsPage creating/>} exact/>*/}
            {/*<Route path="/clinics/update/:id" render={() => <CreateUpdateClinicsPage creating={false}/>} exact/>*/}
            {/*<Route path="/clinics/:id/administrators" component={AdministratorsPage} exact/>*/}
            {/*<Route path="/examination/available/:id" component={ExaminationCalendarPage}/>*/}
            {/*<Route path="/clinics/about/:id" component={ClinicAboutPage} exact/>*/}
            {/*<Route path="/clinics/evaluate/:id" render={() => <EvaluateClinicPage/>} exact/>*/}
            {/*<Route path="/login/" render={() => <AuthorizationPage login/>} exact/>*/}
            {/*<Route path="/login/confirm/" component={ConfirmPasswordlessLoginPage} exact/>*/}
            {/*<Route path="/registration" component={AuthorizationPage} exact/>*/}
            {/*/!* <Route path="/registration-request" component={} exact /> *!/*/}
          </BrowserRouter>
        </>
    );
  }
}

export default App;
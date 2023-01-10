import React from "react";
import StandardLayout from "./../layouts/StandardLayout";
import { Container } from "react-bootstrap";
import {
  faCalendarAlt,
  faCapsules,
  faUserAlt,
  faUserFriends,
} from "@fortawesome/free-solid-svg-icons";
import authService from "../services/auth-service";
import CreateUpdateDoctor from '../components/Doctors/CreateUpdateDoctor/CreateUpdateDoctor';
import CreateUpdatePatients from "../components/Patients/CreateUpdatePatient/CreateUpdatePatient";
import CreateUpdateNurse from "../components/Nurses/CreateUpdateNurses/CreateUpdateNurse";
import CreateUpdateAdministrator from "../components/Administrators/CreateUpdateAdministrator/CreateUpdateAdministrator";

const UpdateProfilePage = (props) => {

  return (
    <StandardLayout>
      <Container>
        {authService.getCurrentRole() === "ROLE_DOCTOR" && <CreateUpdateDoctor creating={false} />}
        {authService.getCurrentRole() === "ROLE_NURSE" && <CreateUpdateNurse creating={false} />}
        {authService.getCurrentRole() === "ROLE_PATIENT" && <CreateUpdatePatients creating={false} />}
        {authService.getCurrentRole() === "ROLE_CLINIC_CENTRE_ADMINISTRATOR" && <CreateUpdateAdministrator creating={false} clinicAdmin={false} />}
        {authService.getCurrentRole() === "ROLE_CLINIC_ADMINISTRATOR" && <CreateUpdateAdministrator creating={false} clinicAdmin/>}
      </Container>
    </StandardLayout>
  );
};

export default UpdateProfilePage;
import React from "react";
import StandardLayout from "./../layouts/StandardLayout";
import { Container } from "react-bootstrap";
import {
  faCalendarAlt,
  faCapsules,
  faUserAlt,
  faUserFriends,
} from "@fortawesome/free-solid-svg-icons";
import CreateUpdateNurse from "../components/Nurses/CreateUpdateNurses/CreateUpdateNurse";

const CreateUpdatePatientPage = (props) => {
  const navLinks = [
    { url: "/patients", icon: faUserFriends, text: "Patients" },
    { url: "/work-calendar", icon: faCalendarAlt, text: "Work calendar" },
    { url: "/recipes", icon: faCapsules, text: "Recipes" },
    { url: "/profile", icon: faUserAlt, text: "Profile" },
  ];

  return (
    <StandardLayout navLinks={navLinks}>
      <Container>
        <CreateUpdateNurse creating={props.creating} />
      </Container>
    </StandardLayout>
  );
};

export default CreateUpdatePatientPage;

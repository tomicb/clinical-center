import React from "react";
import StandardLayout from "./../layouts/StandardLayout";
import { Container } from "react-bootstrap";
import {
  faClinicMedical,
  faNotesMedical
} from "@fortawesome/free-solid-svg-icons";
import PredefinedExaminations from '../components/Examinations/PredefinedExaminations'



const predefinedExaminationPage = (props) => {
  const navLinks = [
    { url: "/clinics", icon: faClinicMedical, text: "Clinics" },
    { url: "/predefinedExaminations", icon: faNotesMedical, text: "Available examinations" }
  ];

  return (
    <StandardLayout navLinks={navLinks}>
      <Container>
        <PredefinedExaminations/>
      </Container>
    </StandardLayout>
  );
};

export default predefinedExaminationPage;
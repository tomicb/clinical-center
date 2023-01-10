import React from "react";
import StandardLayout from "./../layouts/StandardLayout";
import { Container } from "react-bootstrap";
import {
  faClinicMedical
} from "@fortawesome/free-solid-svg-icons";
import ClinicAbout from '../components/Clinics/ClinicAbout/ClinicAbout'



const clinicAboutPage = (props) => {
  const navLinks = [
    { url: "/clinics", icon: faClinicMedical, text: "Clinics" }
  ];

  return (
    <StandardLayout navLinks={navLinks}>
      <Container>
        <ClinicAbout/>
      </Container>
    </StandardLayout>
  );
};

export default clinicAboutPage;
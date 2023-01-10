import React from "react";
import StandardLayout from "./../layouts/StandardLayout";
import { Container } from "react-bootstrap";
import {
  faClinicMedical
} from "@fortawesome/free-solid-svg-icons";
import Clinics from '../components/Clinics/Clinics'



const clinicsSearchPage = (props) => {
  const navLinks = [
    { url: "/clinics", icon: faClinicMedical, text: "Clinics" }
  ];

  return (
    <StandardLayout navLinks={navLinks}>
      <Container>
        <Clinics/>
      </Container>
    </StandardLayout>
  );
};

export default clinicsSearchPage;

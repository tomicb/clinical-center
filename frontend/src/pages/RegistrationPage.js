import React from "react";
import StandardLayout from "./../layouts/StandardLayout";
import RegistrationForm from "../components/Registration/RegistrationForm/RegistrationForm";

const RegistrationPage = (props) => {
  return (
    <StandardLayout navLinks={navLinks}>
      <Container>
        <RegistrationForm />
      </Container>
    </StandardLayout>
  );
};

export default RegistrationPage;

import React from "react";
import AuthorizationLayout from "./../layouts/AuthorizationLayout";
import Login from "./../components/Authorization/Login/Login";
import RegistrationForm from "../components/Authorization/RegistrationForm/RegistrationForm";

const authorizationPage = (props) => {
  let component = <RegistrationForm />;
  if (props.login) {
    component = <Login />;
  }

  return (
    <>
      <AuthorizationLayout>{component}</AuthorizationLayout>
    </>
  );
};

export default authorizationPage;

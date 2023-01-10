import React from 'react';
import StandardLayout from '../layouts/StandardLayout';
import {faChartBar} from "@fortawesome/free-solid-svg-icons";
import ReportClinics from '../components/ClinicReport/ReportClinics'
import ReportDoctors from '../components/ClinicReport/ReportDoctors'
import IncomeChart from '../components/ClinicReport/IncomeChart/IncomeChart'
import { Container } from 'react-bootstrap';

const clinicReportPage = (props) =>{

    const navLinks = [
        {url:'/clinic-reports', icon:faChartBar, text:'Clinic reports'}];

    return(
        <StandardLayout navLinks={navLinks}>
            <Container>
                <h1 style={{textAlign:'center', padding: '15px', color: '#5D001E'}}> Clinic reports </h1>
                <hr></hr>
            </Container>
            <ReportClinics/>
            <ReportDoctors/>
            <IncomeChart/>
        </StandardLayout>
    );
};

export default clinicReportPage;
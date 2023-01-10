import React from 'react';
import {Container} from 'react-bootstrap';
import { Bar  } from 'react-chartjs-2';
import axios from 'axios';

class IncomeChart extends React.Component{

    state = {
        jan: 0,
        feb: 0,
        mar: 0,
        apr: 0,
        may: 0,
        jun: 0,
        jul: 0,
        aug: 0,
        sep: 0,
        oct: 0,
        nov: 0,
        dec: 0
    }

    componentDidMount = () => {
        axios
          .get("/clinic-reports")
          .then((response) => {
            console.log(response)
            this.setState({ jan: response.data.incomes[0],
                            feb: response.data.incomes[1],
                            mar: response.data.incomes[2],
                            apr: response.data.incomes[3],
                            may: response.data.incomes[4],
                            jun: response.data.incomes[5],
                            jul: response.data.incomes[6],
                            aug: response.data.incomes[7],
                            sep: response.data.incomes[8],
                            oct: response.data.incomes[9],
                            nov: response.data.incomes[10],
                            dec: response.data.incomes[11], });
            
          })
          .catch((error) => console.log(error));
      };
    
    render(){
        const data = {
            labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
            datasets: [
              {
                label: 'Incomes in dollars ($)',
                data: [this.state.jan, this.state.feb, this.state.mar, this.state.apr, this.state.may, this.state.jun, this.state.jul, this.state.aug, this.state.sep, this.state.oct, this.state.nov , this.state.dec],
                backgroundColor: [
                  'rgba(255, 99, 132, 0.2)',
                  'rgba(54, 162, 235, 0.2)',
                  'rgba(255, 206, 86, 0.2)',
                  'rgba(75, 192, 192, 0.2)',
                  'rgba(153, 102, 255, 0.2)',
                  'rgba(255, 159, 64, 0.2)',
                  'rgba(255, 159, 64, 0.2)',
                  'rgba(255, 159, 64, 0.2)',
                  'rgba(255, 159, 64, 0.2)',
                  'rgba(255, 159, 64, 0.2)',
                  'rgba(255, 159, 64, 0.2)',
                  'rgba(255, 159, 64, 0.2)',
                ],
                borderColor: [
                  'rgba(255, 99, 132, 1)',
                  'rgba(54, 162, 235, 1)',
                  'rgba(255, 206, 86, 1)',
                  'rgba(75, 192, 192, 1)',
                  'rgba(153, 102, 255, 1)',
                  'rgba(255, 159, 64, 1)',
                  'rgba(255, 159, 64, 1)',
                  'rgba(255, 159, 64, 1)',
                  'rgba(255, 159, 64, 1)',
                  'rgba(255, 159, 64, 1)',
                  'rgba(255, 159, 64, 1)',
                  'rgba(255, 159, 64, 1)',
                ],
                borderWidth: 2,
              },
            ],
          }
          
          const options = {
            maintainAspectRatio: true,
            scales: {
              yAxes: [
                {
                  ticks: {
                    beginAtZero: false,
                  },
                },
              ],
            },
          }

        return(
            <Container>
                <h2 style={{textAlign:'center', padding: '15px',color: '#9A1750'}}> Clinic incomes {new Date().getFullYear()}</h2>
                    <Bar data={data} options={options}/>
                <hr style={{color: '#5D001E'}}></hr>
            </Container>
        );
    };
}

export default IncomeChart;
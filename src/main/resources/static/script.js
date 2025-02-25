document.addEventListener('DOMContentLoaded', function() {
    fetchSeats();
});

function fetchSeats() {
    fetch('/api/seats')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('seats-table');
            tableBody.innerHTML = '';
            data.forEach(seat => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${seat.seatNumber}</td>
                    <td>${seat.booked ? 'Booked' : 'Available'}</td>
                    <td>
                        <button class="btn btn-primary" onclick="bookSeat(${seat.id})" ${seat.booked ? 'disabled' : ''}>
                            ${seat.booked ? 'Booked' : 'Book'}
                        </button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        });
}

function bookSeat(seatId) {
    fetch(`/api/seats/${seatId}/book`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            traineeId: 'T123',
            name: 'John Doe',
            groupName: 'Group A'
        })
    })
    .then(response => {
        if (response.ok) {
            alert('Seat booked successfully');
            fetchSeats();
        } else {
            alert('Failed to book seat');
        }
    });
}

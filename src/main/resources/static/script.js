// Remove the absolute API_URL since it's now relative
// const API_URL = 'http://localhost:8080/api';

function showSection(sectionId) {
    document.querySelectorAll('.section').forEach(section => section.classList.remove('active'));
    document.getElementById(sectionId).classList.add('active');
}

async function addPurchase(event) {
    event.preventDefault();
    const form = event.target;
    const data = new URLSearchParams(new FormData(form)).toString();
    console.log('Sending purchase data:', data);
    try {
        const response = await fetch('/api/stock/purchase', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: data,
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`HTTP error! status: ${response.status}, message: ${errorText}`);
        }
        const result = await response.json();
        console.log('Purchase added:', result);
        alert('Purchase added successfully!');
        form.reset();
    } catch (error) {
        console.error('Fetch error:', error);
        alert('Failed to add purchase: ' + error.message);
    }
}

async function addSale(event) {
    event.preventDefault();
    const form = event.target;
    const data = new URLSearchParams(new FormData(form)).toString();
    try {
        const response = await fetch('/api/stock/sale', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: data,
        });
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        await response.json();
        alert('Sale added successfully!');
        form.reset();
    } catch (error) {
        alert('Failed to add sale: ' + error.message);
    }
}

async function addIncome(event) {
    event.preventDefault();
    const form = event.target;
    const data = new URLSearchParams(new FormData(form)).toString();
    try {
        const response = await fetch('/api/finance/income', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: data,
        });
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        await response.json();
        alert('Income added successfully!');
        form.reset();
    } catch (error) {
        alert('Failed to add income: ' + error.message);
    }
}

async function addExpense(event) {
    event.preventDefault();
    const form = event.target;
    const data = new URLSearchParams(new FormData(form)).toString();
    try {
        const response = await fetch('/api/finance/expense', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: data,
        });
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        await response.json();
        alert('Expense added successfully!');
        form.reset();
    } catch (error) {
        alert('Failed to add expense: ' + error.message);
    }
}

async function calculateNet() {
    try {
        const response = await fetch('/api/finance/net');
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        const result = await response.text();
        document.getElementById('net-output').textContent = result;
    } catch (error) {
        alert('Failed to calculate net: ' + error.message);
    }
}

function addItem() {
    const container = document.getElementById('items-container');
    const newItem = document.createElement('div');
    newItem.className = 'item-row';
    newItem.innerHTML = `
        <input type="text" name="name" placeholder="Item Name" required>
        <input type="number" name="quantity" placeholder="Quantity" min="1" required>
        <input type="number" name="price" placeholder="Price" min="0" required>
    `;
    container.appendChild(newItem);
}

async function createInvoice(event) {
    event.preventDefault();
    const form = event.target;
    const customerName = form.customerName.value;
    const date = form.date.value;
    const items = Array.from(document.querySelectorAll('.item-row')).map(row => ({
        name: row.querySelector('[name="name"]').value,
        quantity: Number(row.querySelector('[name="quantity"]').value),
        price: Number(row.querySelector('[name="price"]').value),
    }));

    const data = { customerName, date, items };
    try {
        const response = await fetch('/api/invoice', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        });
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        const result = await response.json();
        currentInvoiceId = result.id;
        alert('Invoice created successfully!');
        document.getElementById('pdf-button').classList.remove('hidden');
    } catch (error) {
        alert('Failed to create invoice: ' + error.message);
    }
}

async function generatePdf() {
    if (!currentInvoiceId) return;
    try {
        const response = await fetch(`/api/invoice/${currentInvoiceId}/generate`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        const pdfPath = await response.text();
        window.open(`/${pdfPath}`, '_blank'); // Relative path
    } catch (error) {
        alert('Failed to generate PDF: ' + error.message);
    }
}

showSection('stock');
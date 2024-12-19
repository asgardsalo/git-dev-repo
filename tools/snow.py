import requests
import json

# ServiceNow credentials and endpoint (update with your instance details)
SERVICE_NOW_URL = "https://your_instance.service-now.com/api/now/table/incident"
USER = "your_username"
PASSWORD = "your_password"

def create_ticket(requestor, bu_team, cmdb, implementor_group, implementor_name):
    # Prepare ticket data
    payload = {
        "caller_id": requestor,
        "u_bu_team": bu_team,
        "cmdb_ci": cmdb,
        "assignment_group": implementor_group,
        "assigned_to": implementor_name,
        "short_description": "New ticket created via API",
        "description": (
            f"Requestor: {requestor}\n"
            f"BU Team: {bu_team}\n"
            f"CMDB: {cmdb}\n"
            f"Implementor Group: {implementor_group}\n"
            f"Implementor Name: {implementor_name}"
        ),
    }

    # Send request to ServiceNow
    headers = {"Content-Type": "application/json"}
    response = requests.post(
        SERVICE_NOW_URL,
        auth=(USER, PASSWORD),
        headers=headers,
        data=json.dumps(payload)
    )

    if response.status_code == 201:
        ticket = response.json()
        print(f"Ticket created successfully: {ticket['result']['number']}")
    else:
        print(f"Failed to create ticket: {response.status_code}, {response.text}")

# Example usage (replace with actual values)
if __name__ == "__main__":
    create_ticket(
        requestor="john.doe@example.com",
        bu_team="IT Operations",
        cmdb="Server_XYZ",
        implementor_group="Infrastructure Team",
        implementor_name="Jane Smith"
    )
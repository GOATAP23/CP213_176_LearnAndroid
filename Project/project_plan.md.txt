# Project Plan

graph TD
    A["Car Maintenance & Expense Tracker App"] --> B["MVP Features"]
    A --> C["Future Features"]
    
    B --> B1["1. Add Vehicle"]
    B1 --> B1a["Brand/Model/Year"]
    B1 --> B1b["Current Mileage km"]
    B1 --> B1c["Vehicle Image"]
    
    B --> B2["2. Maintenance Log"]
    B2 --> B2a["Engine Oil"]
    B2 --> B2b["Brake Pads"]
    B2 --> B2c["Tires"]
    B2 --> B2d["Battery"]
    
    B --> B3["3. Expense Calculation"]
    B3 --> B3a["Fuel Cost"]
    B3 --> B3b["Repair & Maintenance Cost"]
    B3 --> B3c["Monthly/Yearly Summary"]
    
    C --> C1["1. Trip Log"]
    C1 --> C1a["Record Drives"]
    C1 --> C1b["Calculate Fuel Usage"]
    
    C --> C2["2. Cloud Sync"]
    C2 --> C2a["Login"]
    C2 --> C2b["Data Backup"]
    
    style A fill:#4A90E2,color:#fff,stroke:#2E5C8A,stroke-width:3px
    style B fill:#7B68EE,color:#fff,stroke:#5A4BA1,stroke-width:2px
    style C fill:#7B68EE,color:#fff,stroke:#5A4BA1,stroke-width:2px
    style B1 fill:#50C878,color:#fff
    style B2 fill:#50C878,color:#fff
    style B3 fill:#50C878,color:#fff
    style C1 fill:#FFB347,color:#fff
    style C2 fill:#FFB347,color:#fff

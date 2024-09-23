# Get Started

Welcome to the official documentation of the Praktikumsplaner! Whether you're a developer looking to contribute or a user aiming to integrate our project into your workflow, this guide will help you get started.

## Overview

Praktikumsplaner is designed to organize junior staff and their internships during their vocational training. 

To get the most out of the Praktikumsplaner, we've organized this documentation into several key sections:

- **[About](/about/):** Learn more about the project's background and goals behind it.
- **[Features](/features/):** Explore the full list of features and capabilities of Praktikumsplaner.
- **[Technical Documentation](/documentation/):** Dive deep into technical details, including setup, configuration, and architectural decisions.
- **[GitHub Repository](https://github.com/it-at-m/Praktikumsplaner):** Access the source code, contribute, and track issues.

## Getting Started

1. **Installation:** Follow our [technical setup](/documentation/guides/local-development/#local-development-1) to set up the Praktikumsplaner on your system.

2. **Contribute:** Interested in contributing? Check out our [contributor's guide](/contribute) for details on how you can make a difference.

3. **Support:** Need help? Open an [issue](https://github.com/it-at-m/Praktikumsplaner/issues/new/choose) to ask for assistance.

## Try it

### Requirements

Verified with the following tools

- maven 3.9
- node 16.20
- npm 8.19

### Start required services

- start backend service with embedded database by running `runLocalNoSecurity` script in `praktikumsplaner-backend`
- start api gateway by running `runLocalNoSecurity` script in `praktikumsplaner-frontend/apigateway`
- start the frontend by running `npm run serve` command in `praktikumsplaner-frontend/frontend` in a terminal
  - open the frontend via printed url within a webbrowser  
  ![Output of npm run serve](/outputOfNpmRunServe.png)  
  *Output of `npm run serve`. Visit `http://127.0.0.1:8081/` to access the UI*

## Join the Community

Become a part of the OpenSourceMunich community to stay up-to-date, share ideas, and collaborate with others.

- **[Munich Open Source Community](https://opensource.muenchen.de/):** Find other great projects of the City of Munich.

We're excited to have you here and can't wait to see what you'll build with the Praktikumsplaner. Happy coding!


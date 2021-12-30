name: Bug Report
description: Something isn't working as expected? Report your bugs here.
labels: "type: bug"
body:
  - type: markdown
    attributes:
      value: |
        # Welcome 👋 

        Thanks for taking the time to fill out this bug report.
        Please fill out each section below. This info allows Waltz maintainers to diagnose (and fix!) your issue as quickly as possible. 

  - type: checkboxes
    attributes:
      label: Preliminary Checks
      description: Please make sure that you verify each checkbox and follow the instructions for them.
      options:
        - label: "This issue is not a duplicate. Before opening a new issue, please search existing issues: https://github.com/finos/waltz/issues"
          required: true
        - label: "This issue is not a question, feature request, RFC, or anything other than a bug report directly related to Waltz. 
          required: true
  - type: textarea
    attributes:
      label: Description
      description: Describe the issue that you're seeing.
      placeholder: Be as precise as you can. Feel free to share screenshots, videos, or data from the devtools of your browser.
    validations:
      required: true
  - type: text
    attributes:
      label: Waltz Version
      description: Please provide the Waltz version number
      placeholder: |
        For example:

        1.38
    validations:
      required: false
  - type: textarea
    attributes:
      label: Steps to Reproduce
      description: Clear steps describing how to reproduce the issue.
      value: |
        1.
        2.
        3.
        ...
    validations:
      required: false
  - type: textarea
    attributes:
      label: Expected Result
      description: Describe what you expected to happen.
    validations:
      required: false
  - type: textarea
    attributes:
      label: Actual Result
      description: Describe what actually happened.
    validations:
      required: false
## Introduction
**PCTH Simulator** is a simple, interactive pharmacology experiment simulation tool designed to help students understand the effects of agonists and antagonists, and improve their pharmacological math skills. The primary objective is to determine the identity and concentration of an unknown drug by comparing its effects to generic agonists and antagnoists.

<br></br>
**Figure 1:** Main interface of PCTH Simulator
<p align = "center">
  <img src=https://github.com/user-attachments/assets/277cdbbe-4556-40f6-b690-1b9b77c82b97>
</p>

## Features

- Simulate drug interactions using established pharmacological equations.
- Experiment with different agonists and antagonists to observe their effects.
- Determine the identity and concentration of an unknown drug.
- Intuitive and user-friendly interface designed for educational purposes.

## Installation

To install the PCTH Simulator app:

1. Go to the [latest release](https://github.com/your-username/your-repository/releases/latest) of this repository.
2. Download the APK file attached to the release.
3. Transfer the APK file to your Android device if necessary.
4. Open the APK file on your device and follow the prompts to install the app.

*You may need to enable installation from unknown sources on your device. This option can typically be found in your device's security settings.*

## Pharmacology Math  
  
**Equation 1**: Concentration-Effect Curve in Absence of an Antagonist [1]  

$$E=\cfrac{E_{max} \cdot C}{C+EC_{50}}$$

Where $E$ is the effect at some concentration $C$, $E_{max}$ is the maximal response that can be produced by the drug and $EC_{50}$ is the concentration of drug that produces half maximal effect.

**Equation 2**: The Schild Equation [1]  

$$\cfrac{C^{'}}{C}=1+\cfrac{I}{K_{I}}$$

Where $C^{'}$ is the concentration of agonist required to produce an effect in the presence of an antagonist with concentration ${I}$, and $C$ is the concentration of agonist required to produce the same effect in the absence of antagnoist. $K_{I}$ is the dissociation constant of the antagonist.

**Equation 3**: Concentration-Effect Curve in the Presence of an Antagonist

$$\frac{E_{\text{max}} \cdot C}{C + EC_{50} \cdot \left(1 + \frac{[I]}{K_{I}}\right)}$$

Combining Equations 1 and 2 yields Equation 3, which is the formula that is used in the app to calculate the effects of each agonist and antagonist at any given time.


## Notes 
- All antagonists are considered to be competitive antagonists.
- For simplicity, the individual effects caused by all agonists will be calculated, then the maximum of the calculated effects will be outputted (i.e. Agonist 1 and Agonist 2 effects don't stack).
- The volume of the hypothetical organ bath is 25 mL, and this stays contant throughout the whole experiment.
- For best results, try to keep the stock volume under 1mL, and the stock concentration under 1 uM.

## References  
[1] Katzung, Bertram G. Basic & Clinical Pharmacology. McGraw Hill Professional, 2004.

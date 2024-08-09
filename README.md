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


## Notes and Questions  
All antagonists are considered to be competitive antagonists <br /><br />
**What happens if two or more agonists are added at the same time?**  
For simplicity, the individual effects caused by all agonists will be calculated, then the maximum of the calculated effects will be outputted <br /><br />
The volume of the organ bath is assumed to be constant for all concentration changes (this is fine, as mixing the bath then draining off the extra volume would keep concentrations the same)
For best results, try to keep the stock volume under 1mL, and the stock concentration under 1 uM.


## References  
[1] Katzung, Bertram G. Basic & Clinical Pharmacology. McGraw Hill Professional, 2004.

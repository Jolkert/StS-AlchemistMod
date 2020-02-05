package theAlchemist.cards;

import basemod.abstracts.CustomCard;

public abstract class AbstractElement extends CustomCard
{
	public int stage; // 0 == basic (default) (fire, water, earth, air)
					  // 1 == stage 1 combination (ice, lighting, quake, nature)
					  // 2 == stage 2 combination (life, death)
	public AbstractElement[] constituents; // defaults to null, array of elements that combine to make this element
	public AbstractElement[] reactants; // defaults to null, array of elements that can be combined with this element
	
	
	public AbstractElement(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target)
	{
		super(id, name, img, cost, rawDescription, type, color, rarity, target);
		this.stage = 0;
		this.constituents = null;
		this.reactants = null;
	}
}

package theAlchemist.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAlchemist.AlchemistMod;
import theAlchemist.characters.TheAlchemist;

import static theAlchemist.AlchemistMod.*;

public class SkillTemplate extends CustomCard
{// Template for a basic defend card
	public static final String ID = AlchemistMod.makeID(SkillTemplate.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	
	public static final String IMAGE = makeCardPath("SkillTemplate.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	
	private static final CardRarity RARITY = CardRarity.BASIC;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheAlchemist.Enums.COLOR_PLATINUM;
	
	private static final int COST = 1;
	private static final int BLOCK = 5;
	private static final int UPGRADE_PLUS_BLOCK = 3;
	
	
	public SkillTemplate()
	{
		super(ID, NAME, IMAGE, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		
		this.baseBlock = BLOCK;
		
		this.tags.add(BaseModCardTags.BASIC_DEFEND); // Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.
		this.tags.add(CardTags.STARTER_DEFEND); // Check these tags cause there might be something you need
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, BLOCK));
	}
	
	@Override
	public void upgrade()
	{
		if (!upgraded)
		{
			upgradeName();
			upgradeBlock(UPGRADE_PLUS_BLOCK);
			initializeDescription();
		}
	}
}
